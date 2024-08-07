package com.kuit.healthmate.user.controller;

import com.kuit.healthmate.global.response.ApiResponse;
import com.kuit.healthmate.user.dto.EditNicknameRequest;
import com.kuit.healthmate.user.dto.EditProfileRequest;
import com.kuit.healthmate.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PatchMapping("/edit/nickname")
    public ApiResponse<Object> editNickname(Long userId, @RequestBody EditNicknameRequest editNicknameRequest) {
        userService.editNickname(userId, editNicknameRequest.getNickname());

        return new ApiResponse<>(null);
    }

    @PatchMapping("/edit/profile")
    public ApiResponse<Object> editProfile(Long userId, @RequestBody EditProfileRequest editProfileRequest) {
        userService.editProfile(userId, editProfileRequest.getProfile());

        return new ApiResponse<>(null);
    }
}
