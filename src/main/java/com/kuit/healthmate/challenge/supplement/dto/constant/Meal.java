package com.kuit.healthmate.challenge.supplement.dto.constant;

import com.kuit.healthmate.global.exception.SupplementException;
import com.kuit.healthmate.global.response.ExceptionResponseStatus;
import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;

@Getter
public enum Meal {
    BREAKFAST("breakfast"),
    LUNCH("lunch"),
    DINNER("dinner"),

    BEFORE_OR_AFTER_MEAL("beforeOrAfterMeal"),
    MINUTES("minutes");

    private final String key;

    private Meal(String key) {
        this.key = key;
    }

    public Meal getConstant(String key) {
        return Arrays.stream(Meal.values())
                .filter(x -> Objects.equals(x.key, key))
                .findAny()
                .orElseThrow(
                        () -> new SupplementException(ExceptionResponseStatus.INVALID_SUPPLEMENT_MEAL_CONSTANT)
                );
    }
}
