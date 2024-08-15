package com.kuit.healthmate.batch.analytics;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuit.healthmate.diagnosis.gpt.domain.GptMonthResult;
import com.kuit.healthmate.diagnosis.gpt.domain.GptResult;
import com.kuit.healthmate.diagnosis.gpt.domain.GptWeekResult;
import com.kuit.healthmate.diagnosis.healthscore.service.UserHealthAverageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataAnalysisAndSaveTasklet implements Tasklet {
    private final UserHealthAverageService userHealthAverageService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("Analysis data...");
        ObjectMapper objectMapper = new ObjectMapper();// 초기화
        int dailyLifeStyleCount = 0;
        int dailyMealCount = 0;
        int dailySleepCount = 0;
        int weekLifeStyleCount = 0;
        int weekMealCount = 0;
        int weekSleepCount = 0;
        int monthLifeStyleCount = 0;
        int monthMealCount = 0;
        int monthSleepCount = 0;

        int cnt = 0, cnt2 = 0, cnt3 = 0;


        String gptTodayJson = (String) chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("today");
        String gptWeekJson = (String) chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("week");
        String gptMonthJson = (String) chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("month");

        if(gptTodayJson != null){
            List<GptResult> gptResults = objectMapper.readValue(gptTodayJson,new TypeReference<>() {});
            for (GptResult gptResult : gptResults) {
                dailyLifeStyleCount += gptResult.getLifeStyleToday().getLifeStyleScore();
                dailyMealCount += gptResult.getMealPatternToday().getDailyMealPatternScore();
                dailySleepCount += gptResult.getSleepPatternToday().getDailySleepPatternScore();
                cnt++;
            }
        }

        if (gptWeekJson != null) {
            List<GptWeekResult> gptWeekResults = objectMapper.readValue(gptWeekJson,new TypeReference<>() {});

            for (GptWeekResult gptWeekResult : gptWeekResults) {
                weekLifeStyleCount += gptWeekResult.getLifeStyleToday().getLifeStyleScore();
                weekMealCount += gptWeekResult.getMealPatternToday().getDailyMealPatternScore();
                weekSleepCount += gptWeekResult.getSleepPatternToday().getDailySleepPatternScore();
                cnt2++;
            }

        }

        if (gptMonthJson != null) {
            List<GptMonthResult> gptMonthResults = objectMapper.readValue(gptMonthJson,new TypeReference<>() {});
            for (GptMonthResult gptMonthResult : gptMonthResults) {
                monthLifeStyleCount += gptMonthResult.getLifeStyleToday().getLifeStyleScore();
                monthMealCount += gptMonthResult.getMealPatternToday().getDailyMealPatternScore();
                monthSleepCount += gptMonthResult.getSleepPatternToday().getDailySleepPatternScore();
                cnt3++;
            }

        }

        userHealthAverageService.updateAverage(dailyLifeStyleCount/cnt,dailyMealCount/cnt,dailySleepCount/cnt,
                weekLifeStyleCount/cnt2,weekMealCount/cnt2,weekSleepCount/cnt2,
                monthLifeStyleCount/cnt3,monthMealCount/cnt3,monthSleepCount/cnt3);



        return RepeatStatus.FINISHED;
    }
}
