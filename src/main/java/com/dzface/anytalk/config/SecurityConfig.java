package com.dzface.anytalk.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

// 환경설정 파일선언
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig implements WebMvcConfigurer {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt 암호화 객체를 Bean으로 등록
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(withDefaults())  // 기본 인증을 활성화
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 생성 정책 설정
                )
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(
                                        new AntPathRequestMatcher("/v2/api-docs/**"),
                                        new AntPathRequestMatcher("/v3/api-docs/**"), // Swagger 3.0 API 문서 경로
                                        new AntPathRequestMatcher("/swagger-resources/**"),
                                        new AntPathRequestMatcher("/swagger-ui/**"), // Swagger UI 경로
                                        new AntPathRequestMatcher("/webjars/**"), // Swagger 관련 웹 자원
                                        new AntPathRequestMatcher("/swagger-ui.html"), // Swagger UI HTML 파일
                                        new AntPathRequestMatcher("/swagger-ui/**"), // Swagger UI 경로
                                        new AntPathRequestMatcher("/configuration/ui"), // Swagger UI 경로
                                        new AntPathRequestMatcher("/configuration/security"), // Swagger UI 경로
                                        new AntPathRequestMatcher("auth/login"),
                                        new AntPathRequestMatcher("auth/signup"),
                                        new AntPathRequestMatcher("h2-console"),
                                        new AntPathRequestMatcher("h2-console/**"),
                                        new AntPathRequestMatcher("/**")

                                ).permitAll()
                                .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )
                .headers(headers ->
                        headers // H2 콘솔 사용을 위한 설정
                                .addHeaderWriter((request, response) -> {
                                    response.setHeader("X-Frame-Options", "ALLOW-FROM http://localhost:8111");
                                })
                );

        return http.build();
    }
}