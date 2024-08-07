package com.kuit.healthmate.diagnosis.sleep.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kuit.healthmate.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "sleepPattern")
@Getter
public class SleepPatternQuestionnaire implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String user_name;

    // 수면 시간 점수
    @Column(nullable = false)
    private int sleepDurationScore;

    // 아침 피로도 점수
    @Column(nullable = false)
    private int morningFatigueScore;

    // 하루 컨디션이 최고인 시간 점수
    @Column(nullable = false)
    private int peakConditionTimeScore;

    // 수면 중 특이사항 점수
    @Column(nullable = false)
    private int sleepRemarkScore;

    // 설문 조사 날짜 및 시간
    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;

    @Builder
    public SleepPatternQuestionnaire(int sleepDurationScore, int morningFatigueScore,int peakConditionTimeScore, int sleepRemarkScore, LocalDateTime timestamp, User user){
        this.sleepDurationScore = sleepDurationScore;
        this.morningFatigueScore = morningFatigueScore;
        this.peakConditionTimeScore = peakConditionTimeScore;
        this.sleepRemarkScore = sleepRemarkScore;
        this.timestamp = timestamp;
        this.user = user;
    }
    // 총합 계산 메소드
    public int calculateTotalScore() {
        return sleepDurationScore + morningFatigueScore + peakConditionTimeScore + sleepRemarkScore;
    }
}
