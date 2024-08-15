package com.kuit.healthmate.batch.week;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kuit.healthmate.chatgpt.util.formatter.week.LifeStyleWeekFomatter;
import com.kuit.healthmate.chatgpt.util.formatter.week.MealPatternWeekFormatter;
import com.kuit.healthmate.chatgpt.util.formatter.week.SleepPatternWeekFormatter;
import com.kuit.healthmate.diagnosis.life.domain.LifeStyleQuestionnaire;
import com.kuit.healthmate.diagnosis.meal.domain.MealPatternQuestionnaire;
import com.kuit.healthmate.diagnosis.sleep.domain.SleepPatternQuestionnaire;
import com.kuit.healthmate.diagnosis.symtom.domain.SymptomQuestionnaire;
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
        String symptomDataJson = (String) chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("symptom");
        List<SymptomQuestionnaire> symptomData = null;
        if(symptomDataJson != null){
            symptomData = objectMapper.readValue(symptomDataJson, new TypeReference<>() {});
        }
        if (lifeDataJson != null) {
            List<LifeStyleQuestionnaire> lifeData = objectMapper.readValue(lifeDataJson, new TypeReference<>() {});
            LifeStyleWeekFomatter formatter = new LifeStyleWeekFomatter();
            Map<Long, String> formattedResponse = formatter.formatResponse(lifeData,symptomData);
            chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("lifeFormattedResponse", formattedResponse);
        }

        if (mealDataJson != null) {
            List<MealPatternQuestionnaire> mealData = objectMapper.readValue(mealDataJson, new TypeReference<>() {});
            MealPatternWeekFormatter formatter = new MealPatternWeekFormatter();
            Map<Long, String> formattedResponse = formatter.formatResponse(mealData,symptomData);
            chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("mealFormattedResponse", formattedResponse);
        }

        if (sleepDataJson != null) {
            List<SleepPatternQuestionnaire> sleepData = objectMapper.readValue(sleepDataJson, new TypeReference<>() {});
            SleepPatternWeekFormatter formatter = new SleepPatternWeekFormatter();
            Map<Long, String> formattedResponse = formatter.formatResponse(sleepData,symptomData);
            chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("sleepFormattedResponse", formattedResponse);
        }

        return RepeatStatus.FINISHED;
    }
}
