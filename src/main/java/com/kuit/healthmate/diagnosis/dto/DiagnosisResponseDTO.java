package com.kuit.healthmate.diagnosis.dto;

import com.kuit.healthmate.chatgpt.dto.response.LifeStyleResponse;
import com.kuit.healthmate.chatgpt.dto.response.MealPatternResponse;
import com.kuit.healthmate.chatgpt.dto.response.SleepPatternResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiagnosisResponseDTO {
    private LifeStyleResponse lifeStyleToday;

    private MealPatternResponse mealPatternToday;

    private SleepPatternResponse sleepPatternToday;

    public DiagnosisResponseDTO(LifeStyleResponse lifestyle, MealPatternResponse meal, SleepPatternResponse sleep) {
        this.lifeStyleToday = lifestyle;
        this.mealPatternToday = meal;
        this.sleepPatternToday = sleep;
    }
}
