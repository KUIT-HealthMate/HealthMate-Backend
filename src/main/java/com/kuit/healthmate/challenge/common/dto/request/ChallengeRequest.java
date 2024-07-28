package com.kuit.healthmate.challenge.common.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class ChallengeRequest {
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Date cannot be null")
    private String startDate;

    @NotNull(message = "Date cannot be null")
    private String endDate;
}
