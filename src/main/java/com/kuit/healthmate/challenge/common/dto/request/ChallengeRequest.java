package com.kuit.healthmate.challenge.common.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ChallengeRequest {
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
}