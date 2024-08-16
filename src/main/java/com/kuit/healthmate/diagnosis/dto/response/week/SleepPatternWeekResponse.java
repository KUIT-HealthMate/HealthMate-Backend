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
    private int riskScore;
    private String riskSymptoms;
    private String challenges;

    public SleepPatternWeekResponse(List<Double>  sleepPatternAverages, List<Integer> sleepPatternScores, String description, int riskScore, String riskSymptoms, String challenges){
        this.sleepPatternAverages = sleepPatternAverages;
        this.sleepPatternScores = sleepPatternScores;
        this.description = description;
        this.riskScore = riskScore;
        this.riskSymptoms = riskSymptoms;
        this.challenges = challenges;
    }
}
