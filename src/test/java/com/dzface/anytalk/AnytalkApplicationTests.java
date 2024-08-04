package com.dzface.anytalk;

import com.dzface.anytalk.entity.Question;
import com.dzface.anytalk.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class AnytalkApplicationTests {
	@Autowired // 순환참조 오류가 발생 할 수 있어 테스트 시에만 사용 권장 Setter 사용
	private QuestionRepository questionRepository;

	@Test
	void testJpa() {
		List<Question> all = this.questionRepository.findAll();
		assertEquals(2, all.size());
		Question q = all.get(0);
		assertEquals("질문제목테스트 입니다.", q.getTitle());

	}

}
