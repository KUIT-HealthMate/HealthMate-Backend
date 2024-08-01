package com.kuit.healthmate.chatgpt.service;

import com.kuit.healthmate.chatgpt.dto.request.RequestDto;
import com.kuit.healthmate.chatgpt.dto.response.LifeStyleToday;
import com.kuit.healthmate.chatgpt.dto.response.MealPatternToday;
import com.kuit.healthmate.chatgpt.dto.response.SleepPatternToday;
import com.kuit.healthmate.diagnosis.dto.PostDiagnosisRequest;

public interface GptService {
    LifeStyleToday getPromptByLifeStyle(PostDiagnosisRequest request);

    MealPatternToday getPromptByMeal(PostDiagnosisRequest request);

    SleepPatternToday getPromptBySleep(PostDiagnosisRequest request);
}
