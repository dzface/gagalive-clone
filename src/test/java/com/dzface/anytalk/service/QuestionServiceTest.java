package com.dzface.anytalk.service;

import com.dzface.anytalk.dto.QuestionDto;
import com.dzface.anytalk.dto.UserDto;
import com.dzface.anytalk.entity.SiteUser;
import com.dzface.anytalk.repository.QuestionRepository;
import com.dzface.anytalk.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional // 테스트 생성 후 데이터 삭제
class QuestionServiceTest {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void createQuestion() {
        // 테스트용 유저 생성
        SiteUser user = new SiteUser();
        user.setUserId("testUserId");
        user.setName("Test User");
        userRepository.save(user);

        // 100개의 질문 생성
        for (int i = 1; i <= 100; i++) {
            QuestionDto questionDto = new QuestionDto();
            questionDto.setTitle("Sample Title " + i);
            questionDto.setContent("This is the content of sample question " + i);

            UserDto userDto = new UserDto();
            userDto.setUserId(user.getUserId());
            questionDto.setUserDto(userDto);

            boolean result = questionService.createQuestion(questionDto);
            Assertions.assertTrue(result, "Question creation failed for item " + i);
        }

        // 생성된 질문의 개수 확인
        long count = questionRepository.count();
        Assertions.assertEquals(100, count, "100 questions should have been created.");
    }
}