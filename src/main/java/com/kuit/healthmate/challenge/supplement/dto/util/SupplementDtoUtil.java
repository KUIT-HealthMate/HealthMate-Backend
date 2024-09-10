package com.kuit.healthmate.challenge.supplement.dto.util;

import com.kuit.healthmate.challenge.supplement.domain.SupplementTime;
import com.kuit.healthmate.challenge.supplement.dto.CustomTime;
import com.kuit.healthmate.challenge.supplement.dto.constant.Meal;
import com.kuit.healthmate.challenge.supplement.dto.constant.WeekOfDays;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SupplementDtoUtil {

    public static String weeklyIntakeFrequencyToSelectedDay(Map<String, Boolean> weeklyIntakeFrequency) {
        StringBuilder selectedDay = new StringBuilder();
        for(WeekOfDays days : WeekOfDays.values()) {
            if(weeklyIntakeFrequency.get(days.getKey())) {
                selectedDay.append("1");
            } else {
                selectedDay.append("0");
            }
        }
        return selectedDay.toString();
    }

    public static Map<String, Boolean> selectedDayToWeeklyIntakeFrequency(String selectedDay) {
        Map<String, Boolean> weeklyIntakeFrequency = new HashMap<>();

        int idx = 0;
        for(WeekOfDays days : WeekOfDays.values()) {
            Boolean value;
            if(selectedDay.charAt(idx) == '0') {
                value = Boolean.FALSE;
            } else {
                value = Boolean.TRUE;
            }
            weeklyIntakeFrequency.put(days.getKey(), value);
        }
        return weeklyIntakeFrequency;
    }

    public static List<LocalTime> customTimesToLocalTimes(List<CustomTime> customTimes) {
        return customTimes.stream()
                .map(CustomTime::toLocalTime)
                .collect(Collectors.toList());
    }

    public static List<CustomTime> supplementTimesToCustomTimes(List<SupplementTime> supplementTimes) {
        return supplementTimes.stream()
                .map(SupplementTime::getTime)
                .map(CustomTime::ofLocalTime)
                .collect(Collectors.toList());
    }

    public static int intakeTimeToAfterMeal(Map<String, Integer> intakeTime) {
        Integer beforeOrAfter = intakeTime.get(Meal.BEFORE_OR_AFTER_MEAL.getKey());
        Integer minutes = intakeTime.get(Meal.MINUTES.getKey());
        if (beforeOrAfter == 1) {
            return -minutes;
        }
        return minutes;
    }

    public static Map<String, Integer> afterMealToIntakeTime(int afterMeal) {
        Map<String, Integer> intakeTime = new HashMap<>();

        if(afterMeal >= 0) {
            intakeTime.put("beforeOrAfterMeal", 2);
            intakeTime.put("minutes", afterMeal);
        }
        if (afterMeal < 0) {
            intakeTime.put("beforeOrAfterMeal", 1);
            intakeTime.put("minutes", -afterMeal);
        }

        return intakeTime;
    }

    public static Map<String, Boolean> mealToDailyIntakePeriod(Boolean breakfast, Boolean lunch, Boolean dinner) {
        Map<String, Boolean> dailyIntakePeriod = new HashMap<>();

        dailyIntakePeriod.put("breakfast", breakfast);
        dailyIntakePeriod.put("lunch", lunch);
        dailyIntakePeriod.put("dinner", dinner);

        return dailyIntakePeriod;
    }
}
