package com.kuit.healthmate.user.domain.onboarding.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;

@Getter
public class OnboardingRequestDto {
    @NotBlank(message = "gender: {NotBlank}")
    private int gender;
    @NotBlank(message = "ageGroup: {NotBlank}")
    private int ageGroup;
    private List<String> symptoms;
    @NotBlank(message = "purpose: {NotBlank}")
    private int purpose;
}