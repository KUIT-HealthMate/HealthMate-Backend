package com.kuit.healthmate.challenge.habit.dto;

import lombok.Getter;

import java.time.LocalDate;


@Getter
public class PutCheckHabitRequest {
    LocalDate date;
}
