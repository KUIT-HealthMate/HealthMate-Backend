package com.kuit.healthmate.domain.habit.entity;

import com.kuit.healthmate.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "habits")
@Data
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "habit_id")
    private Long id;

    @Column(length = 25,nullable = false)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(length = 25,nullable = false)
    private String status;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    @ColumnDefault("0")
    private String selected_day; //0000000. 0000001, 1010111

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "habit",cascade = CascadeType.ALL)
    private List<HabitChecker> habitChecker = new ArrayList<>();

}
