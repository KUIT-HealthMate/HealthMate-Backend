package com.kuit.healthmate.config;

import com.kuit.healthmate.auth.service.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2UserService oAuth2UserService;
    private final AuthenticationSuccessHandler oAuth2LoginSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) // X-Frame-Options 비활성화 (h2-console 사용 시 필요)
                .csrf((csrf) -> csrf.disable())
                .formLogin((login) -> login.disable())
                .httpBasic((basic) -> basic.disable())
                .oauth2Login((oauth2) -> oauth2
                        .successHandler(oAuth2LoginSuccessHandler)
                        .userInfoEndpoint((userInfoEndpointConfig ->
                                userInfoEndpointConfig.userService(oAuth2UserService))))
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/**").permitAll() // 개발중 모든 사용자에게 허용
                        .requestMatchers("/auth/**").denyAll()
                        .anyRequest().authenticated());

        return http.build();
    }
}
