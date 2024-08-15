package com.kuit.healthmate.chatgpt.dto.response;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Embeddable
public class LifeStyleResponse {
    private String description;
    private int regularness;
    private int lifeStyleScore; // 생활 습관 점수
    private int immersion;
    private int posture;
    private int riskScore;
    private String riskSymptoms;
    private String challenges;

    public LifeStyleResponse(String description, int regularness, int lifeStyleScore,
                             int immersion, int posture, int riskScore, String riskSymptoms, String challenges) {
        this.description = description;
        this.regularness = regularness;
        this.lifeStyleScore = lifeStyleScore;
        this.immersion = immersion;
        this.posture = posture;
        this.riskScore = riskScore;
        this.riskSymptoms = riskSymptoms;
        this.challenges = challenges;
    }
}
