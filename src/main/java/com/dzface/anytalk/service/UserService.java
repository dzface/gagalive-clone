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
    // 회원 가입 여부 확인
    public boolean isUser(String userId) {
        return userRepository.existsByUserId(userId);
    }
    // 회원 가입
    public boolean signup(UserDto userDto) {
        try {
            SiteUser user = convertDtoToEntity(userDto);
            userRepository.save(user);
            return true; // Return true if member is successfully saved
        } catch (Exception e) {
            // Handle any exceptions that may occur during signup
            log.error("Error occurred during signup: {}", e.getMessage(), e);
            return false; // Return false in case of an error
        }
    }
    // 로그인
    public boolean login(String userId, String password) {
        Optional<SiteUser> user = userRepository.findByUserIdAndPassword(userId, password);
        log.info("user: {}", user);
        return user.isPresent();
    }

    // 회원 Dto -> Entity
    private SiteUser convertDtoToEntity(UserDto userDto) {
        SiteUser user = new SiteUser();
        user.setUserId(userDto.getUserId());
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        return user;
    }
}
