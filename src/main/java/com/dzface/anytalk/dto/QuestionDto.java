//QuestionDto.java
package com.dzface.anytalk.dto;

import com.dzface.anytalk.entity.Comment;
import com.dzface.anytalk.entity.SiteUser;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter@Setter
public class QuestionDto {
    private Long id;
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "Content is mandatory")
    private String content;
    private List<Comment> commentList;
    private UserDto userDto;

}
