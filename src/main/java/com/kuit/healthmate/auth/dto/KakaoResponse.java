package com.kuit.healthmate.security;

import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
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
}