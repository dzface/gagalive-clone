package com.dzface.anytalk;

import com.dzface.anytalk.entity.Question;
import com.dzface.anytalk.repository.QuestionRepository;
import com.dzface.anytalk.service.QuestionService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class AnytalkApplicationTests {
	@Autowired // 순환참조 오류가 발생 할 수 있어 테스트 시에만 사용 권장 Setter 사용
	private QuestionService questionService;

	@Test
	void testJpa() {
		for (int i = 1; i < 101; i++) {
			String title = String.format("테스트 데이터 입니다 : [%03d]",i);
			String content = "내용없음";
			this.questionService.createQuestion(title, content);
		}
	}
}
