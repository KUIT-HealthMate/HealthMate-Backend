package com.kuit.healthmate.user.domain.onboarding.repository;

import com.kuit.healthmate.user.domain.User;
import com.kuit.healthmate.user.domain.onboarding.domain.OnboardingInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnboardingInfoRepository extends JpaRepository<OnboardingInfo, Long> {
}