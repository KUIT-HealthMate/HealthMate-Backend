package com.kuit.healthmate.user.domain.onboarding.service;

import com.kuit.healthmate.challenge.supplement.repository.UserRepository;
import com.kuit.healthmate.global.exception.UserException;
import com.kuit.healthmate.global.response.ExceptionResponseStatus;
import com.kuit.healthmate.user.domain.User;
import com.kuit.healthmate.user.domain.onboarding.domain.OnboardingInfo;
import com.kuit.healthmate.user.domain.onboarding.repository.OnboardingInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OnboardingService {
    private OnboardingInfoRepository onboardingInfoRepository;

    private UserRepository userRepository;
    @Transactional
    public OnboardingInfo saveOnboardingInfo(Long userId, int gender, int ageGroup, List<String> symptoms, int purpose) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ExceptionResponseStatus.INVALID_USER_ID));

        OnboardingInfo onboardingInfo = OnboardingInfo.builder()
                .user(user)
                .gender(gender)
                .ageGroup(ageGroup)
                .symptoms(symptoms)
                .purpose(purpose).build();
        return onboardingInfoRepository.save(onboardingInfo);
    }
}
