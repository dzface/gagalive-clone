package com.dzface.anytalk.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    @NotEmpty(message = "내용을 입력해주세요.")
    private String content;
}
