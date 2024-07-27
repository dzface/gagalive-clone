package com.dzface.anytalk.controller;

//ChatMsgController.java

// SimpMessagingTemplate를 통해 메시지 전달방법 사용

import com.dzface.anytalk.dto.ChatMsgDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class ChatMsgController {
    private final SimpMessagingTemplate messagingTemplate;

    public ChatMsgController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat/send") // 클라이언트가 /app/chat/send로 메시지보낼때 함수 실행
    public void sendMessage(ChatMsgDto chatMsgDto) {
        try {
            log.debug("Received message: {}", chatMsgDto);
            //convertAndSend("/topic/엔드포인트/" + 구독 주소, json형태의 메시지)
            messagingTemplate.convertAndSend("/topic/room/" + chatMsgDto.getRoomId(), chatMsgDto);
            // 구독이 아닌 특정 유저에게 보내는 코드 우리는 사용 안함
            messagingTemplate.convertAndSendToUser(chatMsgDto.getReceiver(), "/queue/reply", chatMsgDto);
        } catch (Exception e) {
            log.error("Error handling message: " + e.getMessage(), e);
        }
    }
}
