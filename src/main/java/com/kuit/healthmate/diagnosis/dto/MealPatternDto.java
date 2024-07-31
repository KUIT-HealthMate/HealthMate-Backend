package com.kuit.healthmate.diagnosis.dto;

import lombok.Getter;

@Getter
public class MealPatternDto {
    private int mealTimeScore;
    private int foodType;
    private int regularMealTimeScore;
    private int mealDurationScore;
    private int seasoningConsumptionScore;
    private int screenUsage;
    private int mealRemark;
}

