package com.dzface.anytalk;

import com.dzface.anytalk.entity.Question;
import com.dzface.anytalk.repository.QuestionRepository;
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
	private QuestionRepository questionRepository;

	@Test
	void testJpa() {
		List<Question> qlist = this.questionRepository.findByTitleLike("%질문%");
		Question q = qlist.get(0);
		assertEquals("질문", q);

	}

}
