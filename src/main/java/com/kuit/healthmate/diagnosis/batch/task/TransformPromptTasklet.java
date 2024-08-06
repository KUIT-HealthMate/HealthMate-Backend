package com.kuit.healthmate.diagnosis.batch.task;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kuit.healthmate.chatgpt.util.formatter.LifeStyleWeekFomatter;
import com.kuit.healthmate.chatgpt.util.formatter.MealPatternWeekFormatter;
import com.kuit.healthmate.chatgpt.util.formatter.SleepPatternWeekFormatter;
import com.kuit.healthmate.diagnosis.life.domain.LifeStyleQuestionnaire;
import com.kuit.healthmate.diagnosis.meal.domain.MealPatternQuestionnaire;
import com.kuit.healthmate.diagnosis.sleep.domain.SleepPatternQuestionnaire;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class TransformPromptTasklet implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("Transforming prompt...");
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        String lifeDataJson = (String) chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("life");
        String mealDataJson = (String) chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("meal");
        String sleepDataJson = (String) chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("sleep");

        if (lifeDataJson != null) {
            List<LifeStyleQuestionnaire> lifeData = objectMapper.readValue(lifeDataJson, new TypeReference<>() {});
            LifeStyleWeekFomatter formatter = new LifeStyleWeekFomatter();
            Map<String, String> formattedResponse = formatter.formatResponse(lifeData);
            chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("lifeFormattedResponse", formattedResponse);
        }

        if (mealDataJson != null) {
            List<MealPatternQuestionnaire> mealData = objectMapper.readValue(mealDataJson, new TypeReference<>() {});
            MealPatternWeekFormatter formatter = new MealPatternWeekFormatter();
            Map<String, String> formattedResponse = formatter.formatResponse(mealData);
            chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("mealFormattedResponse", formattedResponse);
        }

        if (sleepDataJson != null) {
            List<SleepPatternQuestionnaire> sleepData = objectMapper.readValue(sleepDataJson, new TypeReference<>() {});
            SleepPatternWeekFormatter formatter = new SleepPatternWeekFormatter();
            Map<String, String> formattedResponse = formatter.formatResponse(sleepData);
            chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("sleepFormattedResponse", formattedResponse);
        }

        return RepeatStatus.FINISHED;
    }
}
