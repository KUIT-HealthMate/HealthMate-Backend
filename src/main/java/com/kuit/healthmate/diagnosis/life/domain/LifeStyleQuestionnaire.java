package com.kuit.healthmate.diagnosis.life.domain;

import com.kuit.healthmate.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "lifestyle")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "lifeStyle")
@Getter
public class LifeStyleQuestionnaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 규칙적/불규칙적 점수
    @Column(nullable = false)
    private int environmentScore;

    // 집중 시간 점수
    @Column(nullable = false)
    private int focusTimeScore;

    // 커피 소비 점수
    @Column(nullable = false)
    private int coffeeConsumptionScore;

    // 운동 시간 점수
    @Column(nullable = false)
    private int exerciseTimeScore;

    //허리 자세 불편함 점수
    @Column(nullable = false)
    private int postureDiscomfortScore;

    // 설문 조사 날짜 및 시간
    @Column(nullable = false)
    private LocalDateTime timestamp;

    private Long userId;

    // 총합 계산 메소드
    public int calculateTotalScore() {
        return environmentScore + focusTimeScore + coffeeConsumptionScore + exerciseTimeScore + postureDiscomfortScore;
    }
    @Builder
    public LifeStyleQuestionnaire(int environmentScore, int focusTimeScore, int coffeeConsumptionScore, int exerciseTimeScore, int postureDiscomfortScore, LocalDateTime timestamp, Long user){
        this.environmentScore = environmentScore;
        this.focusTimeScore = focusTimeScore;
        this.coffeeConsumptionScore = coffeeConsumptionScore;
        this.exerciseTimeScore = exerciseTimeScore;
        this.postureDiscomfortScore = postureDiscomfortScore;
        this.timestamp = timestamp;
        this.userId = user;
    }

}