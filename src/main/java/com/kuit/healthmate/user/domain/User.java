package com.kuit.healthmate.user.domain;


import com.kuit.healthmate.challenge.supplement.domain.Supplement;
import com.kuit.healthmate.challenge.habit.domain.Habit;
import com.kuit.healthmate.diagnosis.gpt.domain.GptResult;
import com.kuit.healthmate.diagnosis.life.domain.LifeStyleQuestionnaire;
import com.kuit.healthmate.diagnosis.meal.domain.MealPatternQuestionnaire;
import com.kuit.healthmate.diagnosis.sleep.domain.SleepPatternQuestionnaire;
import com.kuit.healthmate.diagnosis.symtom.domain.SymptomQuestionnaire;
import com.kuit.healthmate.user.Gender;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = true,name = "email")
    private String email;

    @Column(nullable = true)
    private String username;

    @Column(nullable = true,name = "nickname")
    private String nickname;

    @Column(nullable = true)
    private String profile;

    @Column(nullable = true)
    private Boolean isAlarm;

    @Column(nullable = true)
    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany
    private List<Habit> habits = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Supplement> supplements = new ArrayList<>();

    @OneToMany
    private List<LifeStyleQuestionnaire> lifeStyleQuestionnaires = new ArrayList<>();

    @OneToMany
    private List<SleepPatternQuestionnaire> sleepPatternQuestionnaires = new ArrayList<>();

    @OneToMany
    private List<MealPatternQuestionnaire> mealPatternQuestionnaires = new ArrayList<>();

    @OneToMany
    private List<SymptomQuestionnaire> symptomQuestionnaires = new ArrayList<>();

    @OneToMany
    private List<GptResult> gptResults = new ArrayList<>();


    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "created_at", nullable = true, updatable = false)
    private LocalDateTime createdAt;
    @Column(nullable = true,name = "balance")
    private Long balance; // 사용자의 현재 코인 잔액

    // 코인 잔액을 증가시키는 메서드
    public void addCoins(Long amount) {
        this.balance += amount;
    }

    // 코인 잔액을 감소시키는 메서드
    public void subtractCoins(Long amount) {
        if (this.balance < amount) {
            this.balance = 0L;
        }
        this.balance -= amount;
    }

    @Builder
    public User(Long id, String username, String profile, Boolean isAlarm, String email, String nickname,LocalDateTime createdAt,Long balance) {
        this.id = id;
        this.username = username;
        this.profile = profile;
        this.isAlarm = isAlarm;
        this.email = email;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.balance = balance;
    }

    public void editNickname(String nickname) {
        this.nickname = nickname;
    }

    public void editProfile(String profile) {
        this.profile = profile;
    }

    public void editAlarmStatus(Boolean isAlarm) {
        this.isAlarm = isAlarm;
    }

    public void setAdditionalInfo(int age, Gender gender) {
        this.age = age;
        this.gender = gender;
    }
}
