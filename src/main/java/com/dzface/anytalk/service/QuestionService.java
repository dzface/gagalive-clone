package com.dzface.anytalk.service;

import com.dzface.anytalk.entity.Question;
import com.dzface.anytalk.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RequiredArgsConstructor // 생성자 생성
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> getQuestionList(){
        return this.questionRepository.findAll();
    }

    public Question getQuestionInfo(Long id) throws DataFormatException {
        Optional<Question> question = this.questionRepository.findAllById(id);
        if (question.isPresent()){
            return question.get();
        } else {
            //DataNotFoundException: 데이터 없을때 발생시키는 오류 404 에러 반환
            throw new DataFormatException("question not found");
        }
    }
}
