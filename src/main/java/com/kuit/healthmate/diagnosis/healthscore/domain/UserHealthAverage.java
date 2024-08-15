package com.kuit.healthmate.diagnosis.healthscore.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_health_average")
public class UserHealthAverage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    // 일간 평균 필드들
    @Column(name = "daily_lifestyle_average")
    private Double dailyLifestyleAverage;

    @Column(name = "daily_sleep_pattern_average")
    private Double dailySleepPatternAverage;

    @Column(name = "daily_meal_pattern_average")
    private Double dailyMealPatternAverage;

    // 주간 평균 필드들
    @Column(name = "weekly_lifestyle_average")
    private Double weeklyLifestyleAverage;

    @Column(name = "weekly_sleep_pattern_average")
    private Double weeklySleepPatternAverage;

    @Column(name = "weekly_meal_pattern_average")
    private Double weeklyMealPatternAverage;

    // 월간 평균 필드들
    @Column(name = "monthly_lifestyle_average")
    private Double monthlyLifestyleAverage;

    @Column(name = "monthly_sleep_pattern_average")
    private Double monthlySleepPatternAverage;

    @Column(name = "monthly_meal_pattern_average")
    private Double monthlyMealPatternAverage;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
