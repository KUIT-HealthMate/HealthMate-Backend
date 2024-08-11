package com.kuit.healthmate.diagnosis.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kuit.healthmate.diagnosis.symtom.domain.SymptomInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class PostDiagnosisRequest {
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

    @NotBlank(message = "date: {NotBlank}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDate date;

}

