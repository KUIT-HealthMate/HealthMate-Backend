package com.kuit.healthmate.batch.week;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuit.healthmate.chatgpt.dto.response.LifeStyleResponse;
import com.kuit.healthmate.chatgpt.dto.response.MealPatternResponse;
import com.kuit.healthmate.chatgpt.dto.response.SleepPatternResponse;
import com.kuit.healthmate.chatgpt.service.GptService;
import com.kuit.healthmate.chatgpt.util.parser.LifeStyleTodayParser;
import com.kuit.healthmate.chatgpt.util.parser.MealPatternTodayParser;
import com.kuit.healthmate.chatgpt.util.parser.SleepPatternParser;
import com.kuit.healthmate.diagnosis.common.service.DiagnosisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendToGptAndSaveTasklet implements Tasklet {
    private final GptService gptService;
    private final DiagnosisService diagnosisService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("Sending data to GPT...");

        LifeStyleTodayParser lifeStyleTodayParser = new LifeStyleTodayParser();
        Map<Long, String> lifeDataJson = (Map<Long, String>) chunkContext.getStepContext()
                .getStepExecution().getJobExecution().getExecutionContext().get("lifeFormattedResponse");

        MealPatternTodayParser mealPatternTodayParser = new MealPatternTodayParser();
        Map<Long, String> mealDataJson = (Map<Long, String>) chunkContext.getStepContext()
                .getStepExecution().getJobExecution().getExecutionContext().get("mealFormattedResponse");

        SleepPatternParser sleepPatternParser = new SleepPatternParser();
        Map<Long, String> sleepDataJson = (Map<Long, String>) chunkContext.getStepContext()
                .getStepExecution().getJobExecution().getExecutionContext().get("sleepFormattedResponse");

        LifeStyleResponse life =null;
        MealPatternResponse meal=null;
        SleepPatternResponse sleep=null;
        ObjectMapper objectMapper = new ObjectMapper();
        if(lifeDataJson == null || mealDataJson ==null ||sleepDataJson ==null){
            throw new RuntimeException();
        }

        for (Long s : lifeDataJson.keySet()) {
            String responseLife = gptService.getPrompt(lifeDataJson.get(s));
            if (responseLife != null) {
                try {
                    life = objectMapper.readValue(responseLife, LifeStyleResponse.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
            String responseMeal = gptService.getPrompt(mealDataJson.get(s));
            if (responseMeal != null) {
                try {
                    meal = objectMapper.readValue(responseMeal, MealPatternResponse.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
            String responseSleep = gptService.getPrompt(sleepDataJson.get(s));
            if (responseSleep != null) {
                try {
                    sleep = objectMapper.readValue(responseSleep, SleepPatternResponse.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
            //GPTMonthResult에 저장
            diagnosisService.saveWeekGptResult(s,life,meal,sleep);
        }
        return RepeatStatus.FINISHED;

    }

}
