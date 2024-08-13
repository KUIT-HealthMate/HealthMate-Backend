package com.kuit.healthmate.challenge.supplement.dto;

import com.kuit.healthmate.challenge.supplement.domain.Supplement;
import com.kuit.healthmate.challenge.supplement.domain.SupplementRoutine;
import com.kuit.healthmate.challenge.supplement.dto.constant.WeekOfDays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SupplementEditResponse {

    private String name;

    private Map<String, Integer> intakeTime;   // 섭취 시간 (식전 1 식후 2, 분 number로)
    private Map<String, Boolean> dailyIntakePeriod;
    private Map<String, Boolean> weeklyIntakeFrequency;

    private List<CustomTime> notificationTime;  //TODO: 얘 해라

    public SupplementEditResponse(Supplement supplement) {
        SupplementRoutine supplementRoutine = supplement.getSupplementRoutine();
        this.name = supplement.getName();
        this.intakeTime = this.getIntakeTime(supplementRoutine);
        this.dailyIntakePeriod = this.getDailyIntakePeriod();
        this.weeklyIntakeFrequency = this.getWeeklyIntakeFrequency();
    }

    private Map<String, Integer> getIntakeTime(SupplementRoutine supplementRoutine) {
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

    private Map<String, Boolean> getDailyIntakePeriod(SupplementRoutine supplementRoutine) {
        Map<String, Boolean> dailyIntakePeriod = new HashMap<>();

        dailyIntakePeriod.put("breakfast", supplementRoutine.getBreakfast());
        dailyIntakePeriod.put("lunch", supplementRoutine.getLunch());
        dailyIntakePeriod.put("dinner", supplementRoutine.getDinner());

        return dailyIntakePeriod;
    }

    private Map<String, Boolean> getWeeklyIntakeFrequency(SupplementRoutine supplementRoutine) {
        Map<String, Boolean> dailyIntakePeriod = new HashMap<>();
        String selectedDay = supplementRoutine.getSelectedDay();

        int idx = 0;
        for(WeekOfDays days : WeekOfDays.values()) {
            Boolean value;
            if(selectedDay.charAt(idx) == '0') {
                value = Boolean.FALSE;
            } else {
                value = Boolean.TRUE;
            }
            dailyIntakePeriod.put(days.getKey(), value);
        }
        return dailyIntakePeriod;
    }
}
