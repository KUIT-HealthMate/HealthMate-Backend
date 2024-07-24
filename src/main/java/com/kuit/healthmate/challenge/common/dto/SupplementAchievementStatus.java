package com.kuit.healthmate.challenge.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplementAchievementStatus {
    private Boolean morning; // 아침 달성 여부
    private Boolean noon;    // 점심 달성 여부
    private Boolean evening; // 저녁 달성 여부

}
