package com.kuit.healthmate.diagnosis.dto.response.day;

import com.kuit.healthmate.chatgpt.dto.response.SleepPatternResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SleepPatternResponseWithAverage {
    private Double sleepAverage;
    private Double sleepRegularityAverage;
    private Double sleepQualityAverage;
    private Double sleepFocusAverage;
    private SleepPatternResponse sleepPatternResponse;

    public SleepPatternResponseWithAverage(Double sleepAverage, Double  sleepRegularityAverage, Double sleepQualityAverage, Double sleepFocusAverage,SleepPatternResponse sleepPatternResponse){
        this.sleepAverage = sleepAverage;
        this.sleepRegularityAverage = sleepRegularityAverage;
        this.sleepQualityAverage = sleepQualityAverage;
        this.sleepFocusAverage = sleepFocusAverage;
        this.sleepPatternResponse = sleepPatternResponse;
    }
}
