package com.kuit.healthmate.diagnosis.dto;

import com.kuit.healthmate.diagnosis.symtom.domain.SymptomInfo;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class PostDiagnosisRequest {
    @NotNull(message = "lifeStyleDto: {Notnull}")
    LifeStyleDto lifeStyleDto;

    @NotNull(message = "mealPatternDto: {Notnull}")
    MealPatternDto mealPatternDto;

    @NotNull(message = "sleepPatternDto: {Notnull}")
    SleepPatternDto sleepPatternDto;

    @NotNull(message = "symptomInfos: {Notnull}")
    List<SymptomInfo> symptomInfos;

}

