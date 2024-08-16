package com.kuit.healthmate.diagnosis.dto.response.month;

import com.kuit.healthmate.diagnosis.dto.response.week.LifeStyleWeekResponse;
import com.kuit.healthmate.diagnosis.dto.response.week.MealPatternWeekResponse;
import com.kuit.healthmate.diagnosis.dto.response.week.SleepPatternWeekResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class DiagnosisMonthResponseDTO {
    private LocalDate date;

    private LifeStyleWeekResponse life;

    private MealPatternWeekResponse meal;

    private SleepPatternWeekResponse sleep;

    public DiagnosisMonthResponseDTO(LocalDate date,LifeStyleWeekResponse lifestyle, MealPatternWeekResponse meal, SleepPatternWeekResponse sleep) {
        this.date = date;
        this.life = lifestyle;
        this.meal = meal;
        this.sleep = sleep;
    }

}