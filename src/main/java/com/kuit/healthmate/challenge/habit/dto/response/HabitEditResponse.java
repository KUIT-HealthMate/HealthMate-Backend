package com.kuit.healthmate.challenge.habit.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kuit.healthmate.challenge.habit.domain.Habit;
import com.kuit.healthmate.challenge.habit.domain.HabitTime;
import com.kuit.healthmate.challenge.supplement.dto.CustomTime;
import com.kuit.healthmate.challenge.supplement.dto.constant.WeekOfDays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor 
public class HabitEditResponse {

    private String name;
    @JsonProperty("id")
    private Long habitId;

    private Map<String, Boolean> weeklyIntakeFrequency;

    private List<CustomTime> notificationTime;

    public HabitEditResponse(Habit habit) {
        this.name = habit.getName();
        this.habitId = habit.getId();
        this.weeklyIntakeFrequency = this.convertWeeklyIntakeFrequency(habit);
        this.notificationTime = this.convertNotificationTime(habit.getHabitTime());
    }


    private Map<String, Boolean> convertWeeklyIntakeFrequency(Habit habit) {
        Map<String, Boolean> weeklyIntakeFrequency = new HashMap<>();
        String selectedDay = habit.getSelectedDay();

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

    private List<CustomTime> convertNotificationTime(List<HabitTime> habitTimes) {
        return habitTimes.stream()
                .map(HabitTime::getTime)
                .map(CustomTime::ofLocalTime)
                .collect(Collectors.toList());
    }
}