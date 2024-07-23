package com.kuit.healthmate.challenge.habit.dto;

import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class PutCheckHabitRequest {
    LocalDateTime date;
}
