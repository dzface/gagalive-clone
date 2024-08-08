//Question.java
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
    @Column(name = "Question_id")
    private Long Id;
    private String title;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    @OneToMany(mappedBy = "question")
    private List<Comment> commentList;
    @ManyToOne
    private SiteUser user;
}
