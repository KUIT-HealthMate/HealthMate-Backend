package com.kuit.healthmate.user.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyPageResponse {

    private String nickname;
    private String profileImage;
    private Integer coin;
    private List<String> health;
}
