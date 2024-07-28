package com.kuit.healthmate.challenge.common.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ChallengeResponse {
    private List<HabitItem> habit;
    private List<SupplementItem> supplement;
    private double achievementRate;
    private String date; // 2024-04-25

    public ChallengeResponse(List<HabitItem> habit, List<SupplementItem> supplement,
                             double achievementRate, String date) {
        this.habit = habit;
        this.supplement = supplement;
        this.achievementRate = achievementRate;
        this.date = date;
    }
}
