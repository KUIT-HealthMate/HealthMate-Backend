package com.kuit.healthmate.dto.supplement;

import com.kuit.healthmate.domain.supplement.SupplementRoutine;
import java.time.LocalTime;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SupplementRegisterRequest {

    private final Long userId;

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
