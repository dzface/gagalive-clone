package com.dzface.anytalk.service;

import com.dzface.anytalk.dto.UserDto;
import com.dzface.anytalk.entity.SiteUser;
import com.dzface.anytalk.errorhandler.DataNotFoundException;
import com.dzface.anytalk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Authenticator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    public SiteUser signup(UserDto userDto){
        SiteUser user = new SiteUser();
        user.setUserId(userDto.getUserId());
        user.setName(userDto.getName());
        // 비밀번호 암호화 객체생성 지금은 객체를 생성했지만 여러곳에서 사용 시 빈으로 등록하고 호출하는 것이 편리
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        this.userRepository.save(user);
        return user;
    }
    public SiteUser getUser(String name) {
        Optional<SiteUser> siteUser = this.userRepository.findByName(name);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }
    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        try {
            Authentication authentication = authenticationService.authenticate(userDto.getName(), userDto.getPassword());
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return ResponseEntity.ok(userDetails);
        } catch (Exception e) {
            log.error("Authentication failed for user: {}", userDto.getName(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
