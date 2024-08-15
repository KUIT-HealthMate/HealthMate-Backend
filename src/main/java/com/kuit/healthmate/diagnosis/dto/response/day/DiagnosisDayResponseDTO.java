package com.kuit.healthmate.diagnosis.dto.response.day;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class DiagnosisDayResponseDTO {
    private LocalDate date;

    private LifeStyleResponseWithAverage life;

    private MealPatternResponseWithAverage meal;

    private SleepPatternResponseWithAverage sleep;

    public DiagnosisDayResponseDTO(LocalDate date,LifeStyleResponseWithAverage lifestyle, MealPatternResponseWithAverage meal, SleepPatternResponseWithAverage sleep) {
        this.date = date;
        this.life = lifestyle;
        this.meal = meal;
        this.sleep = sleep;
    }
}
