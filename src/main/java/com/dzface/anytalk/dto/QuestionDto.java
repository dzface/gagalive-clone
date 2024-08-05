//QuestionDto.java
package com.dzface.anytalk.dto;

import com.dzface.anytalk.entity.Answer;
import com.dzface.anytalk.entity.Comment;
import com.dzface.anytalk.entity.SiteUser;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter@Setter
public class QuestionDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private List<Answer> answerList;
    private List<Comment> commentList;
    private SiteUser author;
}
