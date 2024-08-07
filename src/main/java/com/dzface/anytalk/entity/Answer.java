//Answer.java
package com.dzface.anytalk.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue
    private Long answerId;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    @ManyToOne // 한개의 질문에 답변이 여러개 달릴 수 있으므로
    private Question question; // 질문엔터티 참조하기 위해
    @OneToMany(mappedBy = "answer")
    private List<Comment> commentList;
    @ManyToOne
    private SiteUser author;
}
