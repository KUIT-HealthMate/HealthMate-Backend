package com.kuit.healthmate.challenge.habit.dto.request;


import com.kuit.healthmate.challenge.habit.dto.SelectedTime;
import com.kuit.healthmate.challenge.supplement.dto.CustomTime;
import com.kuit.healthmate.challenge.supplement.dto.constant.WeekOfDays;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.Map;


@Getter
@NoArgsConstructor
public class PostCreateHabitRequest {
    @NotBlank(message = "name: {NotBlank}")
    private String name;

    private Map<String, Boolean> weeklyExecutionFrequency ;

    private List<CustomTime> notificationTime;

    public String getSelectedDay() {  // TODO: ENUM.values는 선언 순대로 가져옴 매번
        StringBuilder selectedDay = new StringBuilder();
        for(WeekOfDays days : WeekOfDays.values()) {
            if(this.weeklyExecutionFrequency .get(days.getKey())) {
                selectedDay.append("1");
            } else {
                selectedDay.append("0");
            }
        }
        return selectedDay.toString();
    }
}

