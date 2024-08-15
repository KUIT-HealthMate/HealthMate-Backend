package com.kuit.healthmate.challenge.supplement.dto;

import com.kuit.healthmate.challenge.supplement.domain.Supplement;
import com.kuit.healthmate.challenge.supplement.domain.SupplementRoutine;
import com.kuit.healthmate.challenge.supplement.domain.SupplementTime;
import com.kuit.healthmate.challenge.supplement.dto.constant.WeekOfDays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor // TODO: MockData로 임시방편
public class SupplementEditResponse {

    private String name;
    private Long supplementId;

    private Map<String, Integer> intakeTime;   // 섭취 시간 (식전 1 식후 2, 분 number로)
    private Map<String, Boolean> dailyIntakePeriod;
    private Map<String, Boolean> weeklyIntakeFrequency;

    private List<CustomTime> notificationTime;

    public SupplementEditResponse(Supplement supplement) {
        SupplementRoutine supplementRoutine = supplement.getSupplementRoutine();
        this.name = supplement.getName();
        this.supplementId = supplement.getId();
        this.intakeTime = this.convertIntakeTime(supplementRoutine);
        this.dailyIntakePeriod = this.convertDailyIntakePeriod(supplementRoutine);
        this.weeklyIntakeFrequency = this.convertWeeklyIntakeFrequency(supplementRoutine);
        this.notificationTime = this.convertNotificationTime(supplement.getSupplementTimes());
    }

    private Map<String, Integer> convertIntakeTime(SupplementRoutine supplementRoutine) {
        Map<String, Integer> intakeTime = new HashMap<>();

        int afterMeal = supplementRoutine.getAfterMeal();

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

    private Map<String, Boolean> convertDailyIntakePeriod(SupplementRoutine supplementRoutine) {
        Map<String, Boolean> dailyIntakePeriod = new HashMap<>();

        dailyIntakePeriod.put("breakfast", supplementRoutine.getBreakfast());
        dailyIntakePeriod.put("lunch", supplementRoutine.getLunch());
        dailyIntakePeriod.put("dinner", supplementRoutine.getDinner());

        return dailyIntakePeriod;
    }

    private Map<String, Boolean> convertWeeklyIntakeFrequency(SupplementRoutine supplementRoutine) {
        Map<String, Boolean> weeklyIntakeFrequency = new HashMap<>();
        String selectedDay = supplementRoutine.getSelectedDay();

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

    private List<CustomTime> convertNotificationTime(List<SupplementTime> supplementTimes) {
        return supplementTimes.stream()
                .map(SupplementTime::getTime)
                .map(CustomTime::ofLocalTime)
                .collect(Collectors.toList());
    }
}
