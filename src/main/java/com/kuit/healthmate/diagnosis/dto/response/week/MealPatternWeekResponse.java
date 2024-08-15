package com.kuit.healthmate.diagnosis.dto.response.week;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MealPatternWeekResponse {
    private List<Double> mealPatternAverages;
    private List<Integer> mealPatternScores;
    private String description;
    private int riskScore;
    private String riskSymptoms;
    private String challenges;

    public MealPatternWeekResponse(List<Double>  mealPatternAverages, List<Integer> mealPatternScores, String description, int riskScore, String riskSymptoms, String challenges){
        this.mealPatternAverages = mealPatternAverages;
        this.mealPatternScores = mealPatternScores;
        this.description = description;
        this.riskScore = riskScore;
        this.riskSymptoms = riskSymptoms;
        this.challenges = challenges;
    }
}
