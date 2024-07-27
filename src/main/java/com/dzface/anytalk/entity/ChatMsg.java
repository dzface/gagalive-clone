package com.dzface.anytalk.entity;

import com.dzface.anytalk.dto.ChatMsgDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString(exclude = "chatRoom") // 순환참조 방지
@Entity
@Table(name = "chat_msg")
public class ChatMsg {
    @Id
    @Column(name = "chat_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long chatId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private String localDateTime ;

    @Enumerated(EnumType.STRING)
    private ChatMsgDto.MessageType type; // MessageType 열거형 사용
    @PrePersist // 날짜가 비어있는경우 현재 시간 자동 입력
    protected void onCreate() {
        if (this.localDateTime == null) {
            this.localDateTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        }
    }
}

