package com.dzface.anytalk.service;

import com.dzface.anytalk.entity.SiteUser;
import com.dzface.anytalk.errorhandler.DataNotFoundException;
import com.dzface.anytalk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    public SiteUser signup(String email, String password, String name){
        SiteUser user = new SiteUser();
        user.setEmail(email);
        user.setName(name);
        // 비밀번호 암호화 객체생성 지금은 객체를 생성했지만 여러곳에서 사용 시 빈으로 등록하고 호출하는 것이 편리
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(password));
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
}
