package com.kuit.healthmate.domain.habit.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

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
}
