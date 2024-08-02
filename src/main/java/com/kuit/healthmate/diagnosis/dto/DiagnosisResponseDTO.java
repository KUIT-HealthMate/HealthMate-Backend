package com.kuit.healthmate.diagnosis.dto;

import com.kuit.healthmate.chatgpt.dto.response.LifeStyleToday;
import com.kuit.healthmate.chatgpt.dto.response.MealPatternToday;
import com.kuit.healthmate.chatgpt.dto.response.SleepPatternToday;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiagnosisResponseDTO {
    private LifeStyleToday lifeStyleToday;

    private MealPatternToday mealPatternToday;

    private SleepPatternToday sleepPatternToday;

    public DiagnosisResponseDTO(LifeStyleToday lifestyle, MealPatternToday meal, SleepPatternToday sleep) {
        this.lifeStyleToday = lifestyle;
        this.mealPatternToday = meal;
        this.sleepPatternToday = sleep;
    }
}
