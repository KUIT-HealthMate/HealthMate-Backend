package com.kuit.healthmate.diagnosis.dto;

import com.kuit.healthmate.diagnosis.symtom.domain.SymptomInfo;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class PostDiagnosisRequest {
    @NotNull(message = "userId: {Notnull}")
    Long userId;
    @NotNull(message = "userName: {Notnull}")
    String userName;

    @NotNull(message = "lifeStyleDto: {Notnull}")
    LifeStyleDto lifeStyleDto;

    @NotNull(message = "mealPatternDto: {Notnull}")
    MealPatternDto mealPatternDto;

    @NotNull(message = "sleepPatternDto: {Notnull}")
    SleepPatternDto sleepPatternDto;

    @NotNull(message = "symptomInfos: {Notnull}")
    List<SymptomInfo> symptomInfos;

}

