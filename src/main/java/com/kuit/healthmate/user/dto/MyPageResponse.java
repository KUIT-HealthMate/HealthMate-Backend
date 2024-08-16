package com.kuit.healthmate.user.dto;

import com.kuit.healthmate.user.domain.User;
import com.kuit.healthmate.user.domain.onboarding.domain.OnboardingInfo;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyPageResponse {

    private String nickname;
    private String profileImage;
    private Integer coin;
    private List<String> health;

    public MyPageResponse(User user, OnboardingInfo onboardingInfo) {
        this.nickname = user.getNickname();
        this.profileImage = user.getProfile();
        this.coin = Math.toIntExact(user.getBalance());
        this.health = onboardingInfo.getSymptoms();
    }
}
