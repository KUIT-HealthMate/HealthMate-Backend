package com.kuit.healthmate.chatgpt.service;

import com.kuit.healthmate.chatgpt.dto.response.LifeStyleResponse;
import com.kuit.healthmate.chatgpt.dto.response.MealPatternResponse;
import com.kuit.healthmate.chatgpt.dto.response.SleepPatternResponse;
import com.kuit.healthmate.diagnosis.dto.PostDiagnosisRequest;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface GptService {
    @Async
    CompletableFuture<LifeStyleResponse> getPromptByLifeStyle(PostDiagnosisRequest request);
    @Async
    CompletableFuture<MealPatternResponse> getPromptByMeal(PostDiagnosisRequest request);
    @Async
    CompletableFuture<SleepPatternResponse> getPromptBySleep(PostDiagnosisRequest request);

    String getPrompt(String request);
}
