package com.kuit.healthmate.diagnosis.dto.response.week;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class LifeStyleWeekResponse {
    private List<Double> lifeStyleAverages;
    private List<Integer> lifeStyleScores;
    private String description;
    private int riskScore;
    private String riskSymptoms;
    private String challenges;

    public LifeStyleWeekResponse(List<Double>  lifeStyleAverages, List<Integer> lifeStyleScores, String description, int riskScore, String riskSymptoms, String challenges){
        this.lifeStyleAverages = lifeStyleAverages;
        this.lifeStyleScores = lifeStyleScores;
        this.description = description;
        this.riskScore = riskScore;
        this.riskSymptoms = riskSymptoms;
        this.challenges = challenges;
    }

}
