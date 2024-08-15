package com.kuit.healthmate.diagnosis.dto.response.week;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class DiagnosisWeekResponseDTO {
    private LocalDate date;

    private LifeStyleWeekResponse life;

    private MealPatternWeekResponse meal;

    private SleepPatternWeekResponse sleep;

    public DiagnosisWeekResponseDTO(LocalDate date,LifeStyleWeekResponse lifestyle, MealPatternWeekResponse meal, SleepPatternWeekResponse sleep) {
        this.date = date;
        this.life = lifestyle;
        this.meal = meal;
        this.sleep = sleep;
    }

}
