package com.kuit.healthmate.challenge.habit.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
public class HabitTime {
    @Id
    @GeneratedValue
    @Column(name = "habitTimeId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habit_id")
    private Habit habit;

    private LocalTime time;


    public HabitTime( Habit habit, LocalTime time){
        this.habit = habit;
        this.time = time;
    }
}
