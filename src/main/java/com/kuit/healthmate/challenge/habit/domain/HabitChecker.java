package com.kuit.healthmate.challenge.habit.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "habitCheckers")
public class HabitChecker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "habitCheck_id")
    private Long id;

    @NotNull
    private LocalDate createdAt;
    
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habit_id")
    private Habit habit;

    public void toggleStatus() {
        this.status = !this.status;
    }

    @Builder
    public HabitChecker(Long id, LocalDate createdAt, Boolean status, Habit habit){
        this.id = id;
        this.createdAt = createdAt;
        this.status = status;
        this.habit = habit;
    }
    @Override
    public String toString() {
        return "HabitChecker{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", status=" + status +
                '}';
    }
}
