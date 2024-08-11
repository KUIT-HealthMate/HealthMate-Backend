package com.kuit.healthmate.challenge.habit.dto.response;

import com.kuit.healthmate.challenge.habit.domain.Habit;
import com.kuit.healthmate.challenge.habit.domain.HabitTime;
import com.kuit.healthmate.challenge.habit.dto.SelectedTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class PostCreateHabitResponse {
    private Long habitId;
    private String name;
    private String selectedDay;
    private List<SelectedTime> selectedTimes;

    public PostCreateHabitResponse(Habit habit,List<SelectedTime> selectedTimes){
        this.habitId = habit.getId();
        this.name = habit.getName();
        this.selectedDay = habit.getSelectedDay();
        this.selectedTimes = selectedTimes;
    }
}
