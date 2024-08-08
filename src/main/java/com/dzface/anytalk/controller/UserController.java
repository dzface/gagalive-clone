package com.dzface.anytalk.controller;

import com.dzface.anytalk.dto.UserDto;
import com.dzface.anytalk.entity.SiteUser;
import com.dzface.anytalk.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;


    // 회원 가입 여부 확인
    @GetMapping("/exists/{email}")
    public ResponseEntity<Boolean> userExists(@PathVariable String Id) {
        log.info("userId: {}", Id);
        boolean isTrue = userService.isUser(Id);
        return ResponseEntity.ok(!isTrue);
    }

    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<Boolean> signup(@RequestBody UserDto userDto) {
        log.info("userDto: {}", userDto.getUserId());
        log.info("userDto: {}", userDto.getPassword());
        boolean isTrue = userService.signup(userDto);
        return ResponseEntity.ok(isTrue);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody UserDto userDto) {
        log.info("userDto: {}", userDto.getUserId());
        log.info("userDto: {}", userDto.getPassword());
        boolean isTrue = userService.login(userDto.getUserId(), userDto.getPassword());
        return ResponseEntity.ok(isTrue);
    }
}
