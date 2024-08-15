package com.kuit.healthmate.batch.analytics;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kuit.healthmate.diagnosis.gpt.domain.GptMonthResult;
import com.kuit.healthmate.diagnosis.gpt.domain.GptResult;
import com.kuit.healthmate.diagnosis.gpt.domain.GptWeekResult;
import com.kuit.healthmate.diagnosis.gpt.repository.GptMonthResultRepository;
import com.kuit.healthmate.diagnosis.gpt.repository.GptResultRepository;
import com.kuit.healthmate.diagnosis.gpt.repository.GptWeekResultRepository;
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
public class FetchScoreTasklet implements Tasklet {
    private final GptResultRepository gptResultRepository;
    private final GptMonthResultRepository gptMonthResultRepository;
    private final GptWeekResultRepository gptWeekResultRepository;


    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("Fetching gpt result data...");
        ObjectMapper objectMapper = new ObjectMapper();

        // 일간
        List<GptResult> gptResults = gptResultRepository.findAll();
        String gptTodayJson = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(gptResults);

        // 주간
        List<GptWeekResult> gptWeekResults = gptWeekResultRepository.findAll();
        String gptWeekJson = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(gptWeekResults);

        // 월간
        List<GptMonthResult> gptMonthResults = gptMonthResultRepository.findAll();
        String gptMonthJson = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(gptMonthResults);

        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("today", gptTodayJson);
        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("week", gptWeekJson);
        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("month", gptMonthJson);

        return RepeatStatus.FINISHED;
    }
}
