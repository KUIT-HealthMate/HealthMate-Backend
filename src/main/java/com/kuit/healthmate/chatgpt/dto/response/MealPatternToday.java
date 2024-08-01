package com.kuit.healthmate.chatgpt.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class MealPatternToday {
    private String description;
    private int regularity; // 식사 패턴의 규칙성
    private int alcoholFrequency; // 음주 빈도
    private int nutritionIntake; // 영양분 섭취 정도
    private int dailyMealPatternScore; // 일간 식사 패턴 점수
    private int riskScore; // 위험 점수
    private String riskSymptoms; // 위험 질환
    private String challenges; // 챌린지 추천
}
