package com.kuit.healthmate.batch.month;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kuit.healthmate.diagnosis.life.domain.LifeStyleQuestionnaire;
import com.kuit.healthmate.diagnosis.life.repository.LifeStyleQuestionnaireRepository;
import com.kuit.healthmate.diagnosis.meal.domain.MealPatternQuestionnaire;
import com.kuit.healthmate.diagnosis.meal.repository.MealPatternQuestionnaireRepository;
import com.kuit.healthmate.diagnosis.sleep.domain.SleepPatternQuestionnaire;
import com.kuit.healthmate.diagnosis.sleep.repository.SleepPatternQuestionnaireRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FetchMonthDataTasklet implements Tasklet {
    private final LifeStyleQuestionnaireRepository lifeStyleQuestionnaireRepository;
    private final MealPatternQuestionnaireRepository mealPatternQuestionnaireRepository;
    private final SleepPatternQuestionnaireRepository sleepPatternQuestionnaireRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("Month..Fetching health data...");
        ObjectMapper objectMapper = new ObjectMapper();

        LocalDateTime firstDayOfMonth = LocalDateTime.now().withDayOfMonth(1);
        LocalDateTime lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusDays(1);

        List<LifeStyleQuestionnaire> lifeData = lifeStyleQuestionnaireRepository.findByDate(firstDayOfMonth, lastDayOfMonth);
        String lifeDataJson = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(lifeData);

        List<MealPatternQuestionnaire> mealData = mealPatternQuestionnaireRepository.findByDate(firstDayOfMonth, lastDayOfMonth);
        String mealDataJson = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(mealData);

        List<SleepPatternQuestionnaire> sleepData = sleepPatternQuestionnaireRepository.findByDate(firstDayOfMonth, lastDayOfMonth);
        String sleepDataJson = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(sleepData);

        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("life", lifeDataJson);
        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("meal", mealDataJson);
        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("sleep", sleepDataJson);

        return RepeatStatus.FINISHED;
    }
}
