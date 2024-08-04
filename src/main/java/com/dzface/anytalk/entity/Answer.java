package com.dzface.anytalk.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue
    private Long id;
    private String content;
    private LocalDateTime createTime;
    @ManyToOne // 한개의 질문에 답변이 여러개 달릴 수 있으므로
    private Question question; // 질문엔터티 참조하기 위해
}