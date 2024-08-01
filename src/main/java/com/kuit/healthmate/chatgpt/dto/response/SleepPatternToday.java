package com.kuit.healthmate.chatgpt.dto.response;

public class SleepPatternToday {
    private String description;
    private int regularity; // 수면 패턴의 규칙성
    private int sleepQuality; // 수면의 질
    private int sleepFocus; // 수면의 집중도
    private int dailySleepPatternScore; // 일간 수면 패턴 점수
    private int riskScore; // 위험 점수
    private String riskSymptoms; // 위험 질환
    private String challenges; // 챌린지 추천
}
