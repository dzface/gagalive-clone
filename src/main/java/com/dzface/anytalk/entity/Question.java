package com.dzface.anytalk.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createTime;
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) // 질문에서 답변을 참조 할 수 있도록 연관관계 설정 질문이 삭제되면 답변도 함께 삭제되도록 cascade 설정
    private List<Answer> answerList;
}
