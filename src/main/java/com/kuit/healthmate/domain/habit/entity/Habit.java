package com.kuit.healthmate.domain.habit.entity;

import com.kuit.healthmate.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "habits")
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "habit_id")
    private Long id;

    @Column(length = 25,nullable = false)
    private String name;

    @Column(length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    private HabitStatus status;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    @ColumnDefault("0000000")
    @Column(length = 7, nullable = false)
    private String selected_day; //월,화,수,목,금,토,알 중에 선택한 날짜를 2진수로 표현

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "habit",cascade = CascadeType.ALL)
    private List<HabitChecker> habitChecker = new ArrayList<>();

    @Builder
    public Habit(Long id, String name, String description, String status, LocalDateTime createdAt, String selected_day) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = HabitStatus.valueOf(status);
        this.createdAt = createdAt;
        this.selected_day = selected_day;
    }

}
