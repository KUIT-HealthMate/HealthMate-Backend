package com.kuit.healthmate.diagnosis.meal.domain;

import com.kuit.healthmate.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "mealPattern")
@Getter
public class MealPatternQuestionnaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 식사 시간 점수 (아침, 점심, 저녁)
    @Column(nullable = false)
    private int mealTimeScore;

    // 음식 종류 (한식, 일식, 중식, 양식, 기타)
    @Column(nullable = false)
    private int foodType;

    // 식사 시간의 규칙성 점수
    @Column(nullable = false)
    private int regularMealTimeScore;

    // 한 끼 식사에 소요된 시간 점수
    @Column(nullable = false)
    private int mealDurationScore;

    // 조미료 섭취 점수
    @Column(nullable = false)
    private int seasoningConsumptionScore;

    // 식사 중 TV나 스마트폰 사용 여부
    @Column(nullable = false)
    private int screenUsage;

    // 식사 도중 느낀 특이점
    @Column(nullable = false)
    private int mealRemark;

    // 설문 조사 날짜 및 시간
    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Builder
    public MealPatternQuestionnaire(int mealTimeScore, int foodType, int regularMealTimeScore, int mealDurationScore,int seasoningConsumptionScore, int screenUsage, int mealRemark,LocalDateTime timestamp, User user){
        this.mealTimeScore = mealTimeScore;
        this.foodType =foodType;
        this.regularMealTimeScore =regularMealTimeScore;
        this.seasoningConsumptionScore = seasoningConsumptionScore;
        this.mealDurationScore = mealDurationScore;
        this.screenUsage = screenUsage;
        this.mealRemark = mealRemark;
        this.timestamp = timestamp;
        this.user= user;
    }

    // 총합 계산 메소드
    public int calculateTotalScore() {
        return mealTimeScore + regularMealTimeScore + mealDurationScore + seasoningConsumptionScore;
    }
}