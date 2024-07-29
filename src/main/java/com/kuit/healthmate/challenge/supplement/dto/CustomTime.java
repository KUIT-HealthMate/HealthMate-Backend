package com.kuit.healthmate.challenge.supplement.dto;

import java.time.LocalTime;
import lombok.Getter;

@Getter
public class CustomTime {

    private int hour;
    private int minute;

    public LocalTime toLocalTime() {
        return LocalTime.of(this.hour, this.minute);
    }
}
