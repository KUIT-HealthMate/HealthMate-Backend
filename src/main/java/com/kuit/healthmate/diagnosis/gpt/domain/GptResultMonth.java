package com.kuit.healthmate.diagnosis.gpt.domain;

import com.kuit.healthmate.chatgpt.dto.response.LifeStyleResponse;
import com.kuit.healthmate.chatgpt.dto.response.MealPatternResponse;
import com.kuit.healthmate.chatgpt.dto.response.SleepPatternResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "GptResultMonth")
public class GptResultMonth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private int year;

    private int month;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "description", column = @Column(columnDefinition = "TEXT",name = "description_lifeStyle")),
            @AttributeOverride(name = "riskScore", column = @Column(name = "riskScore_lifeStyle")),
            @AttributeOverride(name = "riskSymptoms", column = @Column(name = "riskSymptoms_lifeStyle")),
            @AttributeOverride(name = "challenges", column = @Column(name = "challenges_lifeStyle"))
    })
    private LifeStyleResponse lifeStyleMonth;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "description", column = @Column(columnDefinition = "TEXT",name = "description_mealPattern")),
            @AttributeOverride(name = "riskScore", column = @Column(name = "riskScore_mealPattern")),
            @AttributeOverride(name = "regularity", column = @Column(name = "regularity_mealPattern")),
            @AttributeOverride(name = "riskSymptoms", column = @Column(name = "riskSymptoms_mealPattern")),
            @AttributeOverride(name = "challenges", column = @Column(name = "challenges_mealPattern"))
    })
    private MealPatternResponse mealPatternMonth;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "description", column = @Column(columnDefinition = "TEXT",name = "description_sleepPattern")),
            @AttributeOverride(name = "riskScore", column = @Column(name = "riskScore_sleepPattern")),
            @AttributeOverride(name = "regularity", column = @Column(name = "regularity_sleepPattern")),
            @AttributeOverride(name = "riskSymptoms", column = @Column(name = "riskSymptoms_sleepPattern")),
            @AttributeOverride(name = "challenges", column = @Column(name = "challenges_sleepPattern"))
    })
    private SleepPatternResponse sleepPatternMonth;
}
