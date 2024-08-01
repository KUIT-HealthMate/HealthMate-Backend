package com.kuit.healthmate.diagnosis.dto;

import lombok.Getter;

@Getter
public class SleepPatternDto {
    private int sleepDurationScore;
    private int morningFatigueScore;
    private int peakConditionTimeScore;
    private int sleepRemarkScore;
}
