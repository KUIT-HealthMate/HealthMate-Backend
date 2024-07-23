package com.kuit.healthmate.challenge.common.dto;

import java.time.LocalDate;
import java.util.List;

public class ChallengeResponse {
    private List<HabitItem> habit;
    private List<SupplementItem> supplement;
    private int achievementRate;
    private LocalDate date; // 2024.04.25

    public ChallengeResponse(List<HabitItem> habit, List<SupplementItem> supplement, int achievementRate, LocalDate date) {
        this.habit = habit;
        this.supplement = supplement;
        this.achievementRate = achievementRate;
        this.date = date;
    }
}
