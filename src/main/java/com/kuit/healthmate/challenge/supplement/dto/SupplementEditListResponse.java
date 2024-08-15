package com.kuit.healthmate.challenge.supplement.dto;

import com.kuit.healthmate.challenge.supplement.domain.Supplement;
import com.kuit.healthmate.challenge.supplement.domain.SupplementRoutine;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SupplementEditListResponse {

    private Long supplementId;
    private String name;
    private int weeklyIntakeCount;
    private Map<String, Integer> intakeTime;
    private int dailyIntakeCount;

    public SupplementEditListResponse(Supplement supplement) {
        this.supplementId = supplement.getId();
        this.name = supplement.getName();
        this.weeklyIntakeCount = this.calculateWeeklyIntakeCount(supplement.getSupplementRoutine().getSelectedDay());
        this.intakeTime = convertIntakeTime(supplement.getSupplementRoutine().getAfterMeal());
        this.dailyIntakeCount = calculateDailyIntakeCount(supplement.getSupplementRoutine());
    }

    private int calculateWeeklyIntakeCount(String selectedDay) {
        int count = 0;
        for(char c : selectedDay.toCharArray()) {
            if(c == '1') {
                count++;
            }
        }
        return count;
    }

    private Map<String, Integer> convertIntakeTime(int afterMeal) {
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

    private int calculateDailyIntakeCount(SupplementRoutine supplementRoutine) {
        int count = 0;
        if (supplementRoutine.getBreakfast()) {
            count++;
        }
        if (supplementRoutine.getLunch()) {
            count++;
        }
        if (supplementRoutine.getDinner()) {
            count++;
        }

        return count;
    }
}
