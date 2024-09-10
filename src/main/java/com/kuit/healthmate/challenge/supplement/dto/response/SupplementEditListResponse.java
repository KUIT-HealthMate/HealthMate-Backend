package com.kuit.healthmate.challenge.supplement.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kuit.healthmate.challenge.supplement.domain.Supplement;
import com.kuit.healthmate.challenge.supplement.domain.SupplementRoutine;
import com.kuit.healthmate.challenge.supplement.domain.SupplementTime;
import com.kuit.healthmate.challenge.supplement.dto.CustomTime;
import com.kuit.healthmate.challenge.supplement.dto.constant.WeekOfDays;
import com.kuit.healthmate.challenge.supplement.dto.util.SupplementDtoUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SupplementEditListResponse {

    private String name;
    @JsonProperty("id")
    private Long supplementId;

    private Map<String, Integer> intakeTime;   // 섭취 시간 (식전 1 식후 2, 분 number로)
    private Map<String, Boolean> dailyIntakePeriod;
    private Map<String, Boolean> weeklyIntakeFrequency;

    private List<CustomTime> notificationTime;

    public SupplementEditListResponse(Supplement supplement) {
        SupplementRoutine supplementRoutine = supplement.getSupplementRoutine();
        this.name = supplement.getName();
        this.supplementId = supplement.getId();
        this.intakeTime = SupplementDtoUtil.afterMealToIntakeTime(supplementRoutine.getAfterMeal());
        this.dailyIntakePeriod = SupplementDtoUtil.mealToDailyIntakePeriod(
                supplementRoutine.getBreakfast(),
                supplementRoutine.getLunch(),
                supplementRoutine.getDinner()
        );
        this.weeklyIntakeFrequency = SupplementDtoUtil.selectedDayToWeeklyIntakeFrequency(supplementRoutine.getSelectedDay());
        this.notificationTime = SupplementDtoUtil.supplementTimesToCustomTimes(supplement.getSupplementTimes());
    }
}
