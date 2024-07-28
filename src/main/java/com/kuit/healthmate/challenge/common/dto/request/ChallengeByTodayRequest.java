package com.kuit.healthmate.challenge.common.dto.request;

import com.kuit.healthmate.global.validate.ValidLocalDate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class ChallengeByTodayRequest {
    @NotNull(message = "User ID cannot be null")
    Long userId;

    @NotNull(message = "Date cannot be null")
    @ValidLocalDate
    String date;
}
