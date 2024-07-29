package com.kuit.healthmate.challenge.supplement.dto.constant;

import com.kuit.healthmate.global.exception.SupplementException;
import com.kuit.healthmate.global.response.ExceptionResponseStatus;
import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;

@Getter
public enum WeekOfDays {

    MONDAY("monday"),
    TUESDAY("tuesday"),
    WEDNESDAY("wednesday"),
    THURSDAY("thursday"),
    FRIDAY("friday"),
    SATURDAY("saturday"),
    SUNDAY("sunday");


    private final String key;

    private WeekOfDays(String key) {
        this.key = key;
    }

    public WeekOfDays getConstant(String key) {
        return Arrays.stream(WeekOfDays.values())
                .filter(x -> Objects.equals(x.key, key))
                .findAny()
                .orElseThrow(
                        () -> new SupplementException(ExceptionResponseStatus.INVALID_SUPPLEMENT_WEEK_OF_DAYS_CONSTANT)
                );
    }
}
