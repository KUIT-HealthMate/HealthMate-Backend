package com.kuit.healthmate.diagnosis.batch;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kuit.healthmate.chatgpt.dto.response.LifeStyleToday;
import com.kuit.healthmate.chatgpt.dto.response.MealPatternToday;
import com.kuit.healthmate.chatgpt.dto.response.SleepPatternToday;
import com.kuit.healthmate.chatgpt.service.GptService;
import com.kuit.healthmate.chatgpt.util.formatter.LifeStyleWeekFomatter;
import com.kuit.healthmate.chatgpt.util.formatter.MealPatternWeekFormatter;
import com.kuit.healthmate.chatgpt.util.formatter.SleepPatternWeekFormatter;
import com.kuit.healthmate.chatgpt.util.parser.LifeStyleTodayParser;
import com.kuit.healthmate.chatgpt.util.parser.MealPatternTodayParser;
import com.kuit.healthmate.chatgpt.util.parser.SleepPatternParser;
import com.kuit.healthmate.diagnosis.life.domain.LifeStyleQuestionnaire;
import com.kuit.healthmate.diagnosis.life.repository.LifeStyleQuestionnaireRepository;
import com.kuit.healthmate.diagnosis.meal.domain.MealPatternQuestionnaire;
import com.kuit.healthmate.diagnosis.meal.repository.MealPatternQuestionnaireRepository;
import com.kuit.healthmate.diagnosis.sleep.domain.SleepPatternQuestionnaire;
import com.kuit.healthmate.diagnosis.sleep.repository.SleepPatternQuestionnaireRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BatchConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final MealPatternQuestionnaireRepository mealPatternQuestionnaireRepository;
    private final LifeStyleQuestionnaireRepository lifeStyleQuestionnaireRepository;
    private final SleepPatternQuestionnaireRepository sleepPatternQuestionnaireRepository;
    private final GptService gptService;


    @Bean
    public Job job() {
        return new JobBuilder("health", jobRepository)
                .start(fetchHealthDataStep())
                .next(transformPromptStep())
                .next(sendToGptStep())
                .build();
    }

    @Bean
    public Step fetchHealthDataStep() {
        return new StepBuilder("fetchHealthDataStep", jobRepository)
                .tasklet(fetchHealthDataTasklet(), transactionManager)
                .build();
    }

    @Bean
    public Step transformPromptStep() {
        return new StepBuilder("transformPromptStep", jobRepository)
                .tasklet(transformPromptTasklet(), transactionManager)
                .build();
    }

    @Bean
    public Step sendToGptStep() {
        return new StepBuilder("sendToGptStep", jobRepository)
                .tasklet(sendToGptAndSaveTasklet(), transactionManager)
                .build();
    }


    // 일단 life만 구현
    @Bean
    public Tasklet fetchHealthDataTasklet() {
        return (contribution, chunkContext) -> {
            log.info("Fetching health data...");
            ObjectMapper objectMapper = new ObjectMapper();

            // 현재 날짜 기준으로 주의 시작일과 종료일 계산
            LocalDateTime today = LocalDateTime.now();
            LocalDateTime startOfWeek = today.with(DayOfWeek.MONDAY); // 주의 시작일 (월요일)
            LocalDateTime endOfWeek = startOfWeek.plusDays(6); // 주의 종료일 (일요일)

            // 데이터를 가져오는 서비스 로직이 필요한데... 지금은 repository로 대체
            List<LifeStyleQuestionnaire> lifeData = lifeStyleQuestionnaireRepository.findByWeek(startOfWeek, endOfWeek);
            String lifeDataJson = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(lifeData);

            // meal
            List<MealPatternQuestionnaire> mealData = mealPatternQuestionnaireRepository.findByWeek(startOfWeek,endOfWeek);
            String mealDataJson = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(mealData);

            // sleep
            List<SleepPatternQuestionnaire> sleepData = sleepPatternQuestionnaireRepository.findByWeek(startOfWeek,endOfWeek);
            String sleepDataJson = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(sleepData);

            chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("life", lifeDataJson);
            chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("meal", mealDataJson);
            chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("sleep", sleepDataJson);

            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Tasklet transformPromptTasklet() {
        return (contribution, chunkContext) -> {
            log.info("Transforming prompt...");
            // 객체를 직접 읽기
            String lifeDataJson = (String) chunkContext.getStepContext().getStepExecution().getJobExecution()
                    .getExecutionContext().get("life");

            String mealDataJson = (String) chunkContext.getStepContext().getStepExecution().getJobExecution()
                    .getExecutionContext().get("meal");

            String sleepDataJson = (String) chunkContext.getStepContext().getStepExecution().getJobExecution()
                    .getExecutionContext().get("sleep");

            if (lifeDataJson != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                List<LifeStyleQuestionnaire> lifeData = objectMapper.registerModule(new JavaTimeModule())
                        .readValue(lifeDataJson, new TypeReference<List<LifeStyleQuestionnaire>>() {});

                LifeStyleWeekFomatter formatter = new LifeStyleWeekFomatter();
                Map<String, String> formattedResponse = formatter.formatResponse(lifeData);

                chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("lifeFormattedResponse", formattedResponse);

            }

            if (mealDataJson != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                List<MealPatternQuestionnaire> mealData = objectMapper.registerModule(new JavaTimeModule())
                        .readValue(mealDataJson, new TypeReference<List<MealPatternQuestionnaire>>() {});

                // formatter 만들기
                MealPatternWeekFormatter formatter = new MealPatternWeekFormatter();
                Map<String, String> formattedResponse2 = formatter.formatResponse(mealData);

                chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("mealFormattedResponse", formattedResponse2);

            }


            if (sleepDataJson != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                List<SleepPatternQuestionnaire> sleepData = objectMapper.registerModule(new JavaTimeModule())
                        .readValue(sleepDataJson, new TypeReference<List<SleepPatternQuestionnaire>>() {});

                SleepPatternWeekFormatter formatter = new SleepPatternWeekFormatter();
                Map<String, String> formattedResponse3 = formatter.formatResponse(sleepData);

                chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("sleepFormattedResponse", formattedResponse3);

            }

            return RepeatStatus.FINISHED;
        };
    }


    // 예외처리를 추가해야함. 만약 데이터가 없다면과 같이 예외처리가 되지 않음
    @Bean
    public Tasklet sendToGptAndSaveTasklet() {
        return (contribution, chunkContext) -> {
            log.info("Sending data to GPT...");

            // 여기에 GPT API 호출 로직을 추가합니다.
            LifeStyleTodayParser lifeStyleTodayParser = new LifeStyleTodayParser();
            Map<String, String>  lifeDataJson = (Map<String, String> ) chunkContext.getStepContext()
                    .getStepExecution().getJobExecution().getExecutionContext().get("lifeFormattedResponse");

            MealPatternTodayParser mealPatternTodayParser = new MealPatternTodayParser();
            Map<String, String> mealDataJson = (Map<String, String> ) chunkContext.getStepContext()
                    .getStepExecution().getJobExecution().getExecutionContext().get("mealFormattedResponse");


            SleepPatternParser sleepPatternParser = new SleepPatternParser();
            Map<String, String> sleepDataJson = (Map<String, String> ) chunkContext.getStepContext()
                    .getStepExecution().getJobExecution().getExecutionContext().get("sleepFormattedResponse");

            LifeStyleToday life;
            MealPatternToday meal;
            SleepPatternToday sleep;

            for (String s : lifeDataJson.values()) {
                String response = gptService.getPrompt(s);
                if (response != null) {
                     life = lifeStyleTodayParser.parse(response);
                }

            }

            for (String value : mealDataJson.values()) {
                String response = gptService.getPrompt(value);
                log.info(response);
                if (response != null) {
                    meal = mealPatternTodayParser.parse(response);
                }
            }

            for (String value : sleepDataJson.values()) {
                String response = gptService.getPrompt(value);
                if (response != null) {
                    sleep = sleepPatternParser.parse(response);
                }
            }

            // 저장 부분에 없음...

            return RepeatStatus.FINISHED;
        };
    }

}
