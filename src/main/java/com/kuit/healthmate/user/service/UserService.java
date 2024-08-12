package com.kuit.healthmate.user.service;

import com.kuit.healthmate.challenge.supplement.repository.UserRepository;
import com.kuit.healthmate.global.exception.UserException;
import com.kuit.healthmate.global.response.ExceptionResponseStatus;
import com.kuit.healthmate.user.Gender;
import com.kuit.healthmate.user.domain.User;
import java.io.IOException;
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
}
