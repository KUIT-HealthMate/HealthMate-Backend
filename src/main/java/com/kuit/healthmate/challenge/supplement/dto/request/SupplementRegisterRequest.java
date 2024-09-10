package com.kuit.healthmate.challenge.supplement.dto.request;

import com.kuit.healthmate.challenge.supplement.domain.SupplementRoutine;
import com.kuit.healthmate.challenge.supplement.dto.CustomTime;
import com.kuit.healthmate.challenge.supplement.dto.util.SupplementDtoUtil;
import com.kuit.healthmate.challenge.supplement.dto.constant.Meal;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class SupplementRegisterRequest {

    private String name;

    private Map<String, Integer> intakeTime;   // 섭취 시간 (식전 1 식후 2, 분 number로)
    private Map<String, Boolean> dailyIntakePeriod;
    private Map<String, Boolean> weeklyIntakeFrequency;

    private List<CustomTime> notificationTime;

    public SupplementRoutine getSupplementRoutine() {
        return SupplementRoutine.builder()
                .afterMeal(SupplementDtoUtil.intakeTimeToAfterMeal(this.intakeTime))
                .selectedDay(SupplementDtoUtil.weeklyIntakeFrequencyToSelectedDay(this.weeklyIntakeFrequency))
                .breakfast(dailyIntakePeriod.get(Meal.BREAKFAST.getKey()))  // Exception이 터질껀데 처리
                .lunch(dailyIntakePeriod.get(Meal.LUNCH.getKey()))
                .dinner(dailyIntakePeriod.get(Meal.DINNER.getKey()))
                .build();
    }
}
