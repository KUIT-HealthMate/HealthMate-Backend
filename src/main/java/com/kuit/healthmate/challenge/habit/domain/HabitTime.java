package com.kuit.healthmate.challenge.habit.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

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

    private String time;

    @Builder
    public HabitTime( Habit habit, String time){
        this.habit = habit;
        this.time = time;
    }
}
