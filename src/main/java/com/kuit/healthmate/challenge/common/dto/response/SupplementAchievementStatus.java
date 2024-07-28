package com.kuit.healthmate.challenge.common.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplementAchievementStatus {
    private Boolean breakfastSuccess; // 아침 달성 여부
    private Boolean lunchSuccess;    // 점심 달성 여부
    private Boolean dinnerSuccess; // 저녁 달성 여부

    private Boolean breakfastRequired;
    private Boolean lunchRequired;
    private Boolean dinnerRequired;

    public SupplementAchievementStatus(Boolean breakfastSuccess, Boolean lunchSuccess, Boolean dinnerSuccess,
                                       Boolean breakfastRequired, Boolean lunchRequired, Boolean dinnerRequired) {
        this.breakfastSuccess = breakfastSuccess;
        this.lunchSuccess = lunchSuccess;
        this.dinnerSuccess = dinnerSuccess;
        this.breakfastRequired = breakfastRequired;
        this.lunchRequired = lunchRequired;
        this.dinnerRequired = dinnerRequired;
    }
}
