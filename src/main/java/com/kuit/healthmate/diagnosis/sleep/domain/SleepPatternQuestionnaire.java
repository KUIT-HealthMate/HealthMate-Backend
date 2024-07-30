package com.kuit.healthmate.diagnosis.sleep.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "sleepPattern")
@Getter
public class SleepPatternQuestionnaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    // 총합 계산 메소드
    public int calculateTotalScore() {
        return sleepDurationScore + morningFatigueScore + peakConditionTimeScore + sleepRemarkScore;
    }
}
