package com.kuit.healthmate.challenge.supplement.dto;

import com.kuit.healthmate.challenge.supplement.domain.SupplementRoutine;
import java.time.LocalTime;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SupplementUpdateRequest {
    private final String name;

    private final int afterMeal;
    private final String selectedDay;
    private final boolean breakfast;
    private final boolean lunch;
    private final boolean dinner;

    private final List<LocalTime> times;

    public SupplementRoutine getSupplementRoutine() {
        return SupplementRoutine.builder()
                .afterMeal(afterMeal)
                .selectedDay(selectedDay)
                .breakfast(breakfast)
                .lunch(lunch)
                .dinner(dinner)
                .build();
    }
}
