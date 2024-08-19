package com.kuit.healthmate.diagnosis.dto.response.week;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SleepPatternWeekResponse {
    private List<Double> sleepPatternAverages;
    private List<Integer> sleepPatternScores;
    private String description;
    private int sleepScore;
    private Double sleepAvgScore;
    private int riskScore;
    private String riskSymptoms;
    private String challenges;

    public SleepPatternWeekResponse(List<Double>  sleepPatternAverages, List<Integer> sleepPatternScores, String description,int sleepScore,Double sleepAvgScore, int riskScore, String riskSymptoms, String challenges){
        this.sleepPatternAverages = sleepPatternAverages;
        this.sleepPatternScores = sleepPatternScores;
        this.description = description;
        this.sleepScore = sleepScore;
        this.sleepAvgScore = sleepAvgScore;
        this.riskScore = riskScore;
        this.riskSymptoms = riskSymptoms;
        this.challenges = challenges;
    }
}
