package com.kuit.healthmate.chatgpt.service;

import com.kuit.healthmate.chatgpt.dto.request.RequestDto;
import com.kuit.healthmate.chatgpt.dto.response.LifeStyleToday;
import com.kuit.healthmate.chatgpt.dto.response.MealPatternToday;
import com.kuit.healthmate.diagnosis.dto.PostDiagnosisRequest;

public interface GptService {
    LifeStyleToday getPrompt(PostDiagnosisRequest request);

    String getPromptByMeal(PostDiagnosisRequest request);

    String getPromptBySleep(PostDiagnosisRequest request);
}
