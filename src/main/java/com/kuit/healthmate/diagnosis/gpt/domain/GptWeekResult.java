package com.kuit.healthmate.diagnosis.gpt.domain;

import com.kuit.healthmate.chatgpt.dto.response.LifeStyleResponse;
import com.kuit.healthmate.chatgpt.dto.response.MealPatternResponse;
import com.kuit.healthmate.chatgpt.dto.response.SleepPatternResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.WeekFields;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "GptWeekResult")
public class GptWeekResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long year;
    private Long month;
    private Long week;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "description", column = @Column(columnDefinition = "TEXT",name = "description_lifeStyle")),
            @AttributeOverride(name = "riskScore", column = @Column(name = "riskScore_lifeStyle")),
            @AttributeOverride(name = "riskSymptoms", column = @Column(name = "riskSymptoms_lifeStyle")),
            @AttributeOverride(name = "challenges", column = @Column(name = "challenges_lifeStyle"))
    })
    private LifeStyleResponse lifeStyleToday;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "description", column = @Column(columnDefinition = "TEXT",name = "description_mealPattern")),
            @AttributeOverride(name = "riskScore", column = @Column(name = "riskScore_mealPattern")),
            @AttributeOverride(name = "regularity", column = @Column(name = "regularity_mealPattern")),
            @AttributeOverride(name = "riskSymptoms", column = @Column(name = "riskSymptoms_mealPattern")),
            @AttributeOverride(name = "challenges", column = @Column(name = "challenges_mealPattern"))
    })
    private MealPatternResponse mealPatternToday;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "description", column = @Column(columnDefinition = "TEXT",name = "description_sleepPattern")),
            @AttributeOverride(name = "riskScore", column = @Column(name = "riskScore_sleepPattern")),
            @AttributeOverride(name = "regularity", column = @Column(name = "regularity_sleepPattern")),
            @AttributeOverride(name = "riskSymptoms", column = @Column(name = "riskSymptoms_sleepPattern")),
            @AttributeOverride(name = "challenges", column = @Column(name = "challenges_sleepPattern"))
    })
    private SleepPatternResponse sleepPatternToday;

    @Builder
    public GptWeekResult(Long userId, Long year,Long week, Long month,LifeStyleResponse lifeStyleToday, MealPatternResponse mealPatternToday, SleepPatternResponse sleepPatternToday){
        this.userId = userId;
        this.year = year;
        this.week =week;
        this.month = month;
        this.lifeStyleToday =lifeStyleToday;
        this.mealPatternToday = mealPatternToday;
        this.sleepPatternToday = sleepPatternToday;
    }
}