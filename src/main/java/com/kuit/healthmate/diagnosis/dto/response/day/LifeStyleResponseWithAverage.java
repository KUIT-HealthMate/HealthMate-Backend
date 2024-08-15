package com.kuit.healthmate.diagnosis.dto.response.day;

import com.kuit.healthmate.chatgpt.dto.response.LifeStyleResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LifeStyleResponseWithAverage {
    private Double lifeAverage;
    private Double lifeRegularnessAverage;
    private Double lifePostureAverage;
    private Double lifeImmersionAverage;
    private LifeStyleResponse lifeStyleResponse;

    public LifeStyleResponseWithAverage(Double lifeAverage, Double  lifeRegularnessAverage, Double lifePostureAverage, Double lifeImmersionAverage, LifeStyleResponse lifeStyleResponse){
        this.lifeAverage = lifeAverage;
        this.lifeRegularnessAverage = lifeRegularnessAverage;
        this.lifePostureAverage = lifePostureAverage;
        this.lifeImmersionAverage = lifeImmersionAverage;
        this.lifeStyleResponse = lifeStyleResponse;
    }
}
