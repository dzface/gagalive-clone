package com.dzface.anytalk.controller;

import com.dzface.anytalk.dto.UserDto;
import com.dzface.anytalk.entity.SiteUser;
import com.dzface.anytalk.service.AuthenticationService;
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
    private final AuthenticationService authenticationService;

    @GetMapping("/signup")
    public ResponseEntity<SiteUser> signup(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.signup(userDto));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        if (userDto.getPassword().isEmpty()) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return  ResponseEntity.badRequest().body("Password mismatch.");
        }
        try{
            SiteUser newUser = userService.signup(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (DataIntegrityViolationException e){
            log.error("Signup failed: User already exists", e);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 등록된 사용자입니다.");
        } catch (Exception e) {
            log.error("Signup failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Signup failed: " + e.getMessage());
        }
    }
//    @GetMapping("/login")
//    public String login() {
//        return "ssssss";
//    }
    @PostMapping("/login2")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        try {
            // 사용자 인증 시도
            Authentication authentication = authenticationService.authenticate(userDto.getUserId(), userDto.getPassword());
            // 인증 성공 시 사용자 정보 반환
            return ResponseEntity.ok(authentication.getPrincipal());
        } catch (Exception e) {
            log.error("Authentication failed for user: {}", userDto.getUserId(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
