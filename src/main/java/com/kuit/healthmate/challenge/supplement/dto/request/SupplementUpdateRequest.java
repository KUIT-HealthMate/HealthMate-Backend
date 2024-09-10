package com.kuit.healthmate.challenge.supplement.dto.request;

import com.kuit.healthmate.challenge.supplement.dto.CustomTime;
import com.kuit.healthmate.challenge.supplement.dto.util.SupplementDtoUtil;
import com.kuit.healthmate.challenge.supplement.dto.constant.Meal;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
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
        return SupplementDtoUtil.weeklyIntakeFrequencyToSelectedDay(this.weeklyIntakeFrequency);
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
        return SupplementDtoUtil.customTimesToLocalTimes(this.notificationTime);
    }
}
