package com.kuit.healthmate.user.controller;

import com.kuit.healthmate.auth.jwt.Jwt;
import com.kuit.healthmate.global.response.ApiResponse;
import com.kuit.healthmate.user.dto.AdditionalInfoRequest;
import com.kuit.healthmate.user.dto.AlarmRequest;
import com.kuit.healthmate.user.dto.EditNicknameRequest;
import com.kuit.healthmate.user.dto.EditProfileRequest;
import com.kuit.healthmate.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PatchMapping("/edit/nickname")
    public ApiResponse<Object> editNickname(@Jwt Long userId, @RequestBody EditNicknameRequest editNicknameRequest) {
        userService.editNickname(userId, editNicknameRequest.getNickname());

        return new ApiResponse<>(null);
    }

    @PatchMapping("/edit/profile")
    public ApiResponse<Object> editProfile(@Jwt Long userId, @RequestBody EditProfileRequest editProfileRequest) {
        userService.editProfile(userId, editProfileRequest.getProfile());

        return new ApiResponse<>(null);
    }

    @PatchMapping("/edit/alarm")
    public ApiResponse<Object> setAlarm(@Jwt Long userId, @RequestBody AlarmRequest alarmRequest) {
        userService.setAlarm(userId, alarmRequest.getIsAlarm());

        return new ApiResponse<>(null);
    }

    @PostMapping("/additionalInfo")
    public ApiResponse<Object> setAdditionalInfo(@Jwt Long userId,
                                                 @RequestBody AdditionalInfoRequest additionalInfoRequest) {
        userService.setAdditionalInfo(userId, additionalInfoRequest.getAge(), additionalInfoRequest.getGender());

        return new ApiResponse<>(null);
    }
}
