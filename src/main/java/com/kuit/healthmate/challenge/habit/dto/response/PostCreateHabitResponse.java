package com.kuit.healthmate.challenge.habit.dto.response;


import com.kuit.healthmate.challenge.habit.domain.Habit;
import com.kuit.healthmate.challenge.supplement.dto.CustomTime;
import com.kuit.healthmate.challenge.supplement.dto.constant.WeekOfDays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public class PostCreateHabitResponse {
    private Long habitId;
    private String name;
    private Map<String, Boolean> weeklyIntakeFrequency;

    private List<CustomTime> notificationTime;

    public PostCreateHabitResponse(Habit habit, List<CustomTime> notificationTime){
        this.habitId= habit.getId();
        this.name = habit.getName();
        this.weeklyIntakeFrequency = habit.getWeeklyIntakeFrequency();
        this.notificationTime = notificationTime;
    }
}
