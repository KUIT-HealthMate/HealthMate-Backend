package com.kuit.healthmate.challenge.habit.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDate;


@Getter
public class PutCheckHabitRequest {
    @NotBlank(message = "date: {NotBlank}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDate date;
}
