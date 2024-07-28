package com.kuit.healthmate.challenge.common.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ChallengeByPeriodResponse {
    List<ChallengeResponse> challengeResponses;
    double totalAchievementRate;

    public ChallengeByPeriodResponse(List<ChallengeResponse> challengeResponses, double totalAchievementRate) {
        this.challengeResponses = challengeResponses;
        this.totalAchievementRate = totalAchievementRate;
    }
}
