package com.kuit.healthmate.diagnosis.batch;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kuit.healthmate.chatgpt.dto.response.LifeStyleToday;
import com.kuit.healthmate.chatgpt.service.GptService;
import com.kuit.healthmate.chatgpt.util.formatter.LifeStyleWeekFomatter;
import com.kuit.healthmate.chatgpt.util.parser.LifeStyleTodayParser;
import com.kuit.healthmate.diagnosis.life.domain.LifeStyleQuestionnaire;
import com.kuit.healthmate.diagnosis.life.repository.LifeStyleQuestionnaireRepository;
import com.kuit.healthmate.diagnosis.meal.repository.MealPatternQuestionnaireRepository;
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

            // 현재 날짜 기준으로 주의 시작일과 종료일 계산
            LocalDateTime today = LocalDateTime.now();
            LocalDateTime startOfWeek = today.with(DayOfWeek.MONDAY); // 주의 시작일 (월요일)
            LocalDateTime endOfWeek = startOfWeek.plusDays(6); // 주의 종료일 (일요일)

            // 데이터를 가져오는 서비스 로직이 필요한데... 지금은 repository로 대체
            List<LifeStyleQuestionnaire> lifeData = lifeStyleQuestionnaireRepository.findByWeek(startOfWeek, endOfWeek);
            ObjectMapper objectMapper = new ObjectMapper();
            String lifeDataJson = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(lifeData);

            chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("life", lifeDataJson);

            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Tasklet transformPromptTasklet() {
        return (contribution, chunkContext) -> {
            log.info("Transforming prompt...");
            // 객체를 직접 읽기
            String healthDataJson = (String) chunkContext.getStepContext().getStepExecution().getJobExecution()
                    .getExecutionContext().get("life");

            if (healthDataJson != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                List<LifeStyleQuestionnaire> lifeData = objectMapper.registerModule(new JavaTimeModule())
                        .readValue(healthDataJson, new TypeReference<List<LifeStyleQuestionnaire>>() {});

                LifeStyleWeekFomatter formatter = new LifeStyleWeekFomatter();
                Map<String, String> formattedResponse = formatter.formatResponse(lifeData);

                chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("formattedResponse", formattedResponse);

            }

            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Tasklet sendToGptAndSaveTasklet() {
        return (contribution, chunkContext) -> {
            log.info("Sending data to GPT...");
            // 여기에 GPT API 호출 로직을 추가합니다.
            LifeStyleTodayParser lifeStyleTodayParser = new LifeStyleTodayParser();
            Map<String, String>  healthDataJson = (Map<String, String> ) chunkContext.getStepContext()
                    .getStepExecution().getJobExecution().getExecutionContext().get("formattedResponse");
            for (String s : healthDataJson.values()) {
                String response = gptService.getPrompt(s);

                if (response != null) {
                    // Use the lifeStyleTodayParser to parse the response
                    LifeStyleToday life = lifeStyleTodayParser.parse(response);
                    log.info(life.getDescription());

                    // 이것도 서비스 로직으로 바꿔야함. 현재는 없기에 db에 직접 연결
                }

            }
            return RepeatStatus.FINISHED;
        };
    }

}
