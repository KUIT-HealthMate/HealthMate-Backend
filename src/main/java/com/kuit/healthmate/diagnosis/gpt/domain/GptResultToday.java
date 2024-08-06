package com.kuit.healthmate.diagnosis.gpt.domain;

import com.kuit.healthmate.chatgpt.dto.response.LifeStyleResponse;
import com.kuit.healthmate.chatgpt.dto.response.MealPatternResponse;
import com.kuit.healthmate.chatgpt.dto.response.SleepPatternResponse;
import com.kuit.healthmate.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "GptResult")
public class GptResultToday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    private LocalDate date;
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
    public GptResultToday(User user, LocalDate date, LifeStyleResponse lifeStyleToday, MealPatternResponse mealPatternToday, SleepPatternResponse sleepPatternToday){
        this.user = user;
        this.date = date;
        this.lifeStyleToday =lifeStyleToday;
        this.mealPatternToday = mealPatternToday;
        this.sleepPatternToday = sleepPatternToday;
    }
}
