package com.kuit.healthmate.user.service;

import com.kuit.healthmate.challenge.supplement.repository.UserRepository;
import com.kuit.healthmate.global.exception.UserException;
import com.kuit.healthmate.global.response.ExceptionResponseStatus;
import com.kuit.healthmate.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void editNickname(Long userId, String nickname) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserException(ExceptionResponseStatus.INVALID_USER_ID)
        );
        user.editNickname(nickname);
    }

    public void editProfile(Long userId, String profile) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserException(ExceptionResponseStatus.INVALID_USER_ID)
        );
        user.editProfile(profile);
    }
}
