package com.kuit.healthmate.challenge.habit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class SelectedTime {
    private int hour;
    private int minute;

    public SelectedTime(int hour, int minute){
        this.hour = hour;
        this.minute = minute;
    }
    public LocalTime toLocalTime() {
        return LocalTime.of(this.hour, this.minute);
    }
}
