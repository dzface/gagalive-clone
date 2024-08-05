package com.dzface.anytalk.dto;

import lombok.Getter;

@Getter
public enum UserRole {
    // 유저 권한 설정
        ADMIN("ROLE_ADMIN"),
        USER("ROLE_USER");
        private final String value;
        UserRole(String value) {
            this.value = value;
        }
}
