package com.kuit.healthmate.challenge.common.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ChallengeByTodayRequest {
    Long userId;
    LocalDate date;
}
