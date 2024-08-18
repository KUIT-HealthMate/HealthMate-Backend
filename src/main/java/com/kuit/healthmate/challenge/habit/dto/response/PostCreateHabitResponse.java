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

    public PostCreateHabitResponse(Habit habit){
        this.habitId= habit.getId();
    }
}
