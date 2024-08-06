package com.kuit.healthmate.diagnosis.batch.task;

import com.kuit.healthmate.chatgpt.dto.response.LifeStyleToday;
import com.kuit.healthmate.chatgpt.dto.response.MealPatternToday;
import com.kuit.healthmate.chatgpt.dto.response.SleepPatternToday;
import com.kuit.healthmate.chatgpt.service.GptService;
import com.kuit.healthmate.chatgpt.util.parser.LifeStyleTodayParser;
import com.kuit.healthmate.chatgpt.util.parser.MealPatternTodayParser;
import com.kuit.healthmate.chatgpt.util.parser.SleepPatternParser;
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

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("Sending data to GPT...");

        LifeStyleTodayParser lifeStyleTodayParser = new LifeStyleTodayParser();
        Map<String, String> lifeDataJson = (Map<String, String>) chunkContext.getStepContext()
                .getStepExecution().getJobExecution().getExecutionContext().get("lifeFormattedResponse");

        MealPatternTodayParser mealPatternTodayParser = new MealPatternTodayParser();
        Map<String, String> mealDataJson = (Map<String, String>) chunkContext.getStepContext()
                .getStepExecution().getJobExecution().getExecutionContext().get("mealFormattedResponse");

        SleepPatternParser sleepPatternParser = new SleepPatternParser();
        Map<String, String> sleepDataJson = (Map<String, String>) chunkContext.getStepContext()
                .getStepExecution().getJobExecution().getExecutionContext().get("sleepFormattedResponse");

        LifeStyleToday life;
        MealPatternToday meal;
        SleepPatternToday sleep;

        for (String s : lifeDataJson.values()) {
            String response = gptService.getPrompt(s);
            if (response != null) {
                life = lifeStyleTodayParser.parse(response);
                // 추가 로직: life 객체를 저장하거나 사용
            }
        }

        for (String value : mealDataJson.values()) {
            String response = gptService.getPrompt(value);
            log.info(response);
            if (response != null) {
                meal = mealPatternTodayParser.parse(response);
                // 추가 로직: meal 객체를 저장하거나 사용
            }
        }

        for (String value : sleepDataJson.values()) {
            String response = gptService.getPrompt(value);
            if (response != null) {
                sleep = sleepPatternParser.parse(response);
                // 추가 로직: sleep 객체를 저장하거나 사용
            }
        }

        return RepeatStatus.FINISHED;
    }

}
