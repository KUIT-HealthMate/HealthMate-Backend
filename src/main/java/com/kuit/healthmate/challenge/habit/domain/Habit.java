package com.kuit.healthmate.challenge.habit.domain;

import com.kuit.healthmate.challenge.common.domain.Status;
import com.kuit.healthmate.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "habits")
@Getter
@ToString
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "habit_id")
    private Long id;

    @Column(length = 25,nullable = false)
    private String name;

    @Column(length = 255)
    private String memo;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @NotNull
    @ColumnDefault("0000000")
    @Column(length = 7, nullable = false)
    private String selectedDay; //월,화,수,목,금,토,알 중에 선택한 날짜를 2진수로 표현

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "habit",cascade = CascadeType.ALL)
    private List<HabitChecker> habitChecker = new ArrayList<>();

    @OneToMany(mappedBy = "habit",cascade = CascadeType.ALL)
    private List<HabitTime> habitTime = new ArrayList<>();

    @Builder
    public Habit(Long id, String name, String memo, String status, LocalDateTime createdAt,LocalDateTime updatedAt, String selectedDay,User user) {
        this.id = id;
        this.name = name;
        this.memo = memo;
        this.status = Status.valueOf(status);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.selectedDay = selectedDay;
        this.user = user;
    }

}
