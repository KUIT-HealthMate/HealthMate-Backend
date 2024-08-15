package com.kuit.healthmate.batch.analytics;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuit.healthmate.chatgpt.util.formatter.week.LifeStyleWeekFomatter;
import com.kuit.healthmate.chatgpt.util.formatter.week.MealPatternWeekFormatter;
import com.kuit.healthmate.diagnosis.gpt.domain.GptMonthResult;
import com.kuit.healthmate.diagnosis.gpt.domain.GptResult;
import com.kuit.healthmate.diagnosis.gpt.domain.GptWeekResult;
import com.kuit.healthmate.diagnosis.life.domain.LifeStyleQuestionnaire;
import com.kuit.healthmate.diagnosis.meal.domain.MealPatternQuestionnaire;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class DataAnalysisAndSaveTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("Analysis data...");
        ObjectMapper objectMapper = new ObjectMapper();

        String gptTodayJson = (String) chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("today");
        String gptWeekJson = (String) chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("week");
        String gptMonthJson = (String) chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("month");

        if(gptTodayJson != null){
            List<GptResult> gptResults = objectMapper.readValue(gptTodayJson,new TypeReference<>() {});
        }
        if (gptWeekJson != null) {
            List<GptWeekResult> gptWeekResults = objectMapper.readValue(gptWeekJson,new TypeReference<>() {});

        }

        if (gptMonthJson != null) {
            List<GptMonthResult> gptMonthResults = objectMapper.readValue(gptMonthJson,new TypeReference<>() {});

        }

        return RepeatStatus.FINISHED;
    }
}
