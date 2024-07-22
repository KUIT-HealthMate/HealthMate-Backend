package com.kuit.healthmate.domain.supplement;

import com.kuit.healthmate.domain.Period;
import com.kuit.healthmate.domain.Status;
import com.kuit.healthmate.domain.user.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "supplements")
public class Supplement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplement_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "supplement", cascade = CascadeType.ALL)
    private List<SupplementChecker> supplementCheckers = new ArrayList<>();

    @OneToMany(mappedBy = "supplement", cascade = CascadeType.ALL)
    private List<SupplementTime> supplementTimes = new ArrayList<>();

    private String name;

    @Embedded
    private Period period;

    @Embedded
    private SupplementRoutine supplementRoutine;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Supplement(User user, String name, SupplementRoutine supplementRoutine) {
        this.user = user;
        this.name = name;
        this.supplementRoutine = supplementRoutine;
    }

    public void setSupplementTimes(List<SupplementTime> supplementTimes) {
        this.supplementTimes = supplementTimes;
    }

    @PrePersist
    protected void init() {
        if (this.status == null) {
            status = Status.ACTIVE;
        }
    }

    public void update(String name, int afterMeal, String selectedDay, boolean breakfast,
                       boolean lunch, boolean dinner, List<LocalTime> times) {
        this.name = name;
        this.supplementRoutine = SupplementRoutine.builder()
                .afterMeal(afterMeal)
                .selectedDay(selectedDay)
                .breakfast(breakfast)
                .lunch(lunch)
                .dinner(dinner)
                .build();
        this.supplementTimes = times.stream()
                .map(localTime -> new SupplementTime(this, localTime))
                .toList();
    }
}
