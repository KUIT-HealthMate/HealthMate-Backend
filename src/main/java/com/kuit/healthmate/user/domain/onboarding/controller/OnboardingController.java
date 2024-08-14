package com.kuit.healthmate.user.domain.onboarding.controller;

import com.kuit.healthmate.auth.jwt.Jwt;
import com.kuit.healthmate.global.exception.BadRequestException;
import com.kuit.healthmate.global.response.ApiResponse;
import com.kuit.healthmate.user.domain.onboarding.domain.OnboardingInfo;
import com.kuit.healthmate.user.domain.onboarding.dto.OnboardingRequestDto;
import com.kuit.healthmate.user.domain.onboarding.service.OnboardingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.kuit.healthmate.global.response.ExceptionResponseStatus.INVALID_HABIT_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/onboarding")
public class OnboardingController {

    private final OnboardingService onboardingService;

    @PostMapping("")
    public ApiResponse<OnboardingInfo> saveOnboardingInfo(@Jwt Long userId, @RequestBody OnboardingRequestDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(INVALID_HABIT_VALUE);
        }
        OnboardingInfo onboardingInfo = onboardingService.saveOnboardingInfo(
                userId,
                requestDto.getGender(),
                requestDto.getAgeGroup(),
                requestDto.getSymptoms(),
                requestDto.getPurpose());

        return new ApiResponse<>(onboardingInfo);
    }
}
