package com.kuit.healthmate.challenge.common.dto;

import com.kuit.healthmate.challenge.habit.domain.Habit;
import com.kuit.healthmate.challenge.habit.domain.HabitChecker;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class HabitItem {
    private String challengeName; // 챌린지명
    private boolean achievementStatus; // 달성여부

    public HabitItem(String challengeName, boolean achievementStatus) {
        this.challengeName = challengeName;
        this.achievementStatus = achievementStatus;
    }

    public static HabitItem fromHabit(Habit habit, LocalDate date){
        return new HabitItem(habit.getName(), habit.getHabitChecker().stream()
                .filter(checker -> checker.getCreatedAt().equals(date))
                .anyMatch(HabitChecker::getStatus));
    }

    public boolean getSuccess(){
        return this.achievementStatus;
    }


}
