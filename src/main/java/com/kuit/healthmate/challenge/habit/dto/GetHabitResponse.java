package com.kuit.healthmate.challenge.habit.dto;

import com.kuit.healthmate.challenge.habit.domain.Habit;
import com.kuit.healthmate.challenge.habit.domain.HabitTime;
import com.kuit.healthmate.challenge.supplement.domain.Supplement;
import com.kuit.healthmate.challenge.supplement.domain.SupplementRoutine;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class GetHabitResponse {
    public GetHabitResponse(Habit habit) {
        this.name = habit.getName();
        this.memo = habit.getMemo();
        this.selectedDay = habit.getSelectedDay();
        this.habitTimes = getHabitTimes();
    }

    private String name;
    private String memo;
    private String selectedDay;
    private List<HabitTime> habitTimes;
}
