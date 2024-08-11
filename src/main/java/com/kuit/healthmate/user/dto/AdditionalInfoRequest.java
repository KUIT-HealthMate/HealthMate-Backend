package com.kuit.healthmate.user.dto;

import com.kuit.healthmate.user.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AdditionalInfoRequest {
    private int age;
    private Gender gender;
}
