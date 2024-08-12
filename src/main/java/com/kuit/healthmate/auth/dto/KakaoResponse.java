package com.kuit.healthmate.auth.dto;

import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@RequiredArgsConstructor
public class KakaoResponse implements OAuth2Response{
    private final Map<String, Object> attribute;

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getNickname() {
        Map<String, String> properties = (Map<String, String>) this.attribute.get("properties");

        return properties.get("nickname");
    }

    @Override
    public String getProfileImage() {
        Map<String, String> properties = (Map<String, String>) this.attribute.get("properties");

        return properties.get("profile_image");
    }
}