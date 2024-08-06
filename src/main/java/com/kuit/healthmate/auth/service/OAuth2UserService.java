package com.kuit.healthmate.user;

import com.kuit.healthmate.challenge.supplement.repository.UserRepository;
import com.kuit.healthmate.security.CustomOAuth2User;
import com.kuit.healthmate.security.KakaoResponse;
import com.kuit.healthmate.security.OAuth2Response;
import com.kuit.healthmate.user.domain.User;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2Response oAuth2Response = null;
        if(Objects.equals(userRequest.getClientRegistration().getRegistrationId(), "kakao")) {
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        }

        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        String nickname = oAuth2Response.getNickname();
        User user = userRepository.findByUsername(username).orElseGet(
                () -> userRepository.save(User.builder()
                        .username(username)
                        .nickname(nickname)
                        .build())
        );

        System.out.println("login_user_id = " + user.getId());

        return new CustomOAuth2User(oAuth2User.getAuthorities(), oAuth2User.getAttributes(),
                userRequest.getClientRegistration().getProviderDetails()
                        .getUserInfoEndpoint().getUserNameAttributeName(), user.getId());
    }
}
