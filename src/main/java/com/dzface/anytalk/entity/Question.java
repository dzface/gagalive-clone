//Question.java
package com.dzface.anytalk.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue
    @Column(name = "Question_id")
    private Long Id;
    private String title;
    private String content;
    private String createTime;
    private String modifyTime;
    @OneToMany(mappedBy = "question")
    private List<Comment> commentList;
    @ManyToOne
    private SiteUser user;
    // 시간 미입력시 자동 생성
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createTime = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
        this.modifyTime = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifyTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }
}
