package com.kuit.healthmate.challenge.supplement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kuit.healthmate.challenge.supplement.domain.SupplementRoutine;
import com.kuit.healthmate.challenge.supplement.dto.constant.Meal;
import com.kuit.healthmate.challenge.supplement.dto.constant.WeekOfDays;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SupplementUpdateRequest {

    private String name;

    private Map<String, Integer> intakeTime;   // 섭취 시간 (식전 1 식후 2, 분 number로)
    private Map<String, Boolean> dailyIntakePeriod;
    private Map<String, Boolean> weeklyIntakeFrequency;

    private List<CustomTime> notificationTime;

    public int getAfterMeal() {
        Integer beforeOrAfter = this.intakeTime.get(Meal.BEFORE_OR_AFTER_MEAL.getKey());
        Integer minutes = this.intakeTime.get(Meal.MINUTES.getKey());
        if (beforeOrAfter == 1) {
            return -minutes;
        }
        return minutes;
    }

    public String getSelectedDay() {  // TODO: ENUM.values는 선언 순대로 가져옴 매번
        StringBuilder selectedDay = new StringBuilder();
        for(WeekOfDays days : WeekOfDays.values()) {
            if(this.weeklyIntakeFrequency.get(days.getKey())) {
                selectedDay.append("1");
            } else {
                selectedDay.append("0");
            }
        }
        return selectedDay.toString();
    }

    public Boolean isBreakfast() {
        return dailyIntakePeriod.get(Meal.BREAKFAST.getKey());
    }

    public Boolean isLunch() {
        return dailyIntakePeriod.get(Meal.LUNCH.getKey());
    }

    public Boolean isDinner() {
        return dailyIntakePeriod.get(Meal.DINNER.getKey());
    }

    public List<LocalTime> getTimes() {
        return this.notificationTime.stream()
                .map(CustomTime::toLocalTime)
                .collect(Collectors.toList());
    }
}
