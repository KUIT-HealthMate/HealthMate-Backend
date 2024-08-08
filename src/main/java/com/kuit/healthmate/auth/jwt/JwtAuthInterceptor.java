package com.kuit.healthmate.auth.jwt;

import static com.kuit.healthmate.global.response.ExceptionResponseStatus.EXPIRED_TOKEN;
import static com.kuit.healthmate.global.response.ExceptionResponseStatus.TOKEN_NOT_FOUND;
import static com.kuit.healthmate.global.response.ExceptionResponseStatus.UNSUPPORTED_TOKEN_TYPE;

import com.kuit.healthmate.global.exception.CustomJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthInterceptor implements HandlerInterceptor {
    private static final String JWT_TOKEN_PREFIX = "Bearer ";

    private final JwtProvider jwtProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String accessToken = resolveAccessToken(request);
        validateAccessToken(accessToken);

        Long userId = jwtProvider.getUserId(accessToken);

        log.info("User Id: " + userId + " intercepted");

        request.setAttribute("userId", userId);

        return true;
    }

    private String resolveAccessToken(HttpServletRequest request) {
        String token = request.getHeader("Jwt");
        validateToken(token);
        return token.substring(JWT_TOKEN_PREFIX.length());
    }

    private void validateToken(String token) {
        if (token == null) {
            throw new CustomJwtException(TOKEN_NOT_FOUND);
        }
        if (!token.startsWith(JWT_TOKEN_PREFIX)) {
            throw new CustomJwtException(UNSUPPORTED_TOKEN_TYPE);
        }
    }

    private void validateAccessToken(String accessToken) {
        if (jwtProvider.isExpiredToken(accessToken)) {
            throw new CustomJwtException(EXPIRED_TOKEN);
        }
    }
}
