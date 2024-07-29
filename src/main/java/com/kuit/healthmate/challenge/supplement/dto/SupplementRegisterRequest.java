package com.kuit.healthmate.challenge.supplement.dto;

import com.kuit.healthmate.challenge.supplement.domain.SupplementRoutine;
import com.kuit.healthmate.challenge.supplement.dto.constant.Meal;
import com.kuit.healthmate.challenge.supplement.dto.constant.WeekOfDays;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class SupplementRegisterRequest {

    private Long userId;    // JWT ??

    private String name;

    private Map<String, Integer> intakeTime;   // 섭취 시간 (식전 1 식후 2, 분 number로)
    private Map<String, Boolean> dailyIntakePeriod;
    private Map<String, Boolean> weeklyIntakeFrequency;

    private List<CustomTime> notificationTime;

    public SupplementRoutine getSupplementRoutine() {
        return SupplementRoutine.builder()
                .afterMeal(getAfterMeal())
                .selectedDay(getSelectedDay())
                .breakfast(dailyIntakePeriod.get(Meal.BREAKFAST.getKey()))  // Exception이 터질껀데 처리
                .lunch(dailyIntakePeriod.get(Meal.LUNCH.getKey()))
                .dinner(dailyIntakePeriod.get(Meal.DINNER.getKey()))
                .build();
    }

    private int getAfterMeal() {
        Integer beforeOrAfter = this.intakeTime.get(Meal.BEFORE_OR_AFTER_MEAL.getKey());
        Integer minutes = this.intakeTime.get(Meal.MINUTES.getKey());
        if (beforeOrAfter == 1) {
            return -minutes;
        }
        return minutes;
    }

    private String getSelectedDay() {  // TODO: ENUM.values는 선언 순대로 가져옴 매번
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
}
