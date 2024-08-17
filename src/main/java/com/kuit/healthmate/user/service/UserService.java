package com.kuit.healthmate.user.service;

import com.kuit.healthmate.global.exception.BadRequestException;
import com.kuit.healthmate.user.domain.onboarding.domain.OnboardingInfo;
import com.kuit.healthmate.user.domain.onboarding.repository.OnboardingInfoRepository;
import com.kuit.healthmate.user.repository.UserRepository;
import com.kuit.healthmate.global.exception.UserException;
import com.kuit.healthmate.global.response.ExceptionResponseStatus;
import com.kuit.healthmate.user.Gender;
import com.kuit.healthmate.user.domain.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProfileImageService profileImageService;
    private final OnboardingInfoRepository onboardingInfoRepository;

    public void editNickname(Long userId, String nickname) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserException(ExceptionResponseStatus.INVALID_USER_ID)
        );
        user.editNickname(nickname);
    }

    public void editProfile(Long userId, MultipartFile profileImage) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserException(ExceptionResponseStatus.INVALID_USER_ID)
        );
        profileImageService.deleteImageFromS3(user.getProfile());

        String imageUrl = profileImageService.uploadImageToS3(profileImage);
        user.editProfile(imageUrl);
    }

    public void setAlarm(Long userId, Boolean on) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserException(ExceptionResponseStatus.INVALID_USER_ID)
        );
        user.editAlarmStatus(on);
    }

    public void setAdditionalInfo(Long userId, int age, Gender gender) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserException(ExceptionResponseStatus.INVALID_USER_ID)
        );
        user.setAdditionalInfo(age, gender);
    }

    public User getUserInfo(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new UserException(ExceptionResponseStatus.INVALID_USER_ID)
        );
    }

    public OnboardingInfo getOnboardingInfo(Long userId) {
        return onboardingInfoRepository.findByUserId(userId).orElseThrow(
                () -> new BadRequestException(ExceptionResponseStatus.INVALID_DIAGNOSIS_VALUE)
        );
    }
}
