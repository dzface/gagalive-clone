package com.dzface.anytalk.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// WebSocketConfig.java
@Configuration //config 파일 선언
@EnableWebSocketMessageBroker // Stomp를 사용하기 위해 선언하는 어노테이션
@RequiredArgsConstructor // 생성자코드 대체
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 소켓통신 연결 요청 엔드 포인트
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("").withSockJS();
    }
    // /topic 구독받고자하는 주소, /queue topic 하위 카테고리 /app 메세지 보낼 주소
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue"); // 메시지 받는 요청 처리
        registry.setApplicationDestinationPrefixes("/app"); //메시지 보내는 요청을 처리
    }
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // 통신전 JWT 인증을 위한 신호주고받기 전에 납치하는자(interceptors) 설정 부분 우리는 사용 제외 함
        // registration.interceptors(stompHandler); // 핸들러 등록
    }
}