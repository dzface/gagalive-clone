package com.dzface.anytalk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// 환경설정 파일선언
@Configuration
// 모든 URL 요청이 스프링시큐리티 제어를 받도록 하는 어노테이션
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                // h2 console CSRF 검증 예외처리
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
                // X-Frame-Options 헤더를 SAMEORIGIN으로 설정
                .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))

                .formLogin((formLogin) -> formLogin
                        //로그인페이지 주소
                        .loginPage("/user/login")
                        //로그인 성공시 이동 주소
                        .defaultSuccessUrl("/"))
                .logout((logout) -> logout
                        //로그아웃 주소
                        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                        // 로그아웃 성공시 이동 주소
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true))
        ;
        return http.build();
    }
    // 비밀번호 암호와 객체 빈
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 스프링 시큐리티 인증처리 객체 빈 UserSecurityService와 PasswordEncoder를 내부적으로 사용
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
