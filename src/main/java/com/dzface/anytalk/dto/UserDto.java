package com.dzface.anytalk.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UserDto {
    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email
    private String userId;
    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password;
    @NotEmpty(message = "이름은 필수 항목입니다.")
    @Size(min=3, max = 30)
    private String name;


}
