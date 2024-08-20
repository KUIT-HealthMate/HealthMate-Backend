package com.kuit.healthmate.auth;

import com.kuit.healthmate.auth.jwt.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        Long userId = oAuth2User.getUserId();
        boolean isNew = oAuth2User.getIsNew();
        String token = jwtProvider.createToken(oAuth2User.getName(), userId);

        log.info("success handler called " + token);

        response.setHeader("Jwt", token);
        response.addCookie(
                new Cookie("Jwt", token)
        );

        response.sendRedirect("http://bepoyong-s3-bucket.s3-website.ap-northeast-2.amazonaws.com/logining?Jwt=" + token + "&isNew=" + isNew);
    }
}

