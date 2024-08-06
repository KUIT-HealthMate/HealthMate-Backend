package com.kuit.healthmate.chatgpt.service;

import com.kuit.healthmate.chatgpt.dto.response.LifeStyleResponse;
import com.kuit.healthmate.chatgpt.dto.response.MealPatternResponse;
import com.kuit.healthmate.chatgpt.dto.response.SleepPatternResponse;
import com.kuit.healthmate.diagnosis.dto.PostDiagnosisRequest;

public interface GptService {
    LifeStyleResponse getPromptByLifeStyle(PostDiagnosisRequest request);

    MealPatternResponse getPromptByMeal(PostDiagnosisRequest request);

    SleepPatternResponse getPromptBySleep(PostDiagnosisRequest request);

    String getPrompt(String request);
}
