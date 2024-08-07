//AnswerDto.java
package com.dzface.anytalk.dto;

import com.dzface.anytalk.entity.Question;
import com.dzface.anytalk.entity.SiteUser;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter@Setter
public class AnswerDto {
    private Long Id;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private Question question;
    private SiteUser author;
}
