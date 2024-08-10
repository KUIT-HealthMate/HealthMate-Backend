package com.kuit.healthmate.diagnosis.dto;

import com.kuit.healthmate.chatgpt.dto.response.LifeStyleResponse;
import com.kuit.healthmate.chatgpt.dto.response.MealPatternResponse;
import com.kuit.healthmate.chatgpt.dto.response.SleepPatternResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class DiagnosisResponseDTO {
    private LocalDate date;

    private LifeStyleResponse lifeStyleToday;

    private MealPatternResponse mealPatternToday;

    private SleepPatternResponse sleepPatternToday;

    public DiagnosisResponseDTO(LocalDate date,LifeStyleResponse lifestyle, MealPatternResponse meal, SleepPatternResponse sleep) {
        this.date = date;
        this.lifeStyleToday = lifestyle;
        this.mealPatternToday = meal;
        this.sleepPatternToday = sleep;
    }
}
