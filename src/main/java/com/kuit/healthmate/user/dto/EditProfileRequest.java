package com.kuit.healthmate.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter
public class EditProfileRequest {

    private MultipartFile profileImage;
}
