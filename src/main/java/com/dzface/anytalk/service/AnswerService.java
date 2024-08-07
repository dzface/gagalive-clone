//AnswerService.java
package com.dzface.anytalk.service;


import com.dzface.anytalk.dto.AnswerDto;
import com.dzface.anytalk.entity.Answer;
import com.dzface.anytalk.entity.Question;
import com.dzface.anytalk.entity.SiteUser;
import com.dzface.anytalk.errorhandler.DataNotFoundException;
import com.dzface.anytalk.repository.AnswerRepository;
import com.dzface.anytalk.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AnswerService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    // 답변 생성
    public boolean createAnswer(Long questionId, AnswerDto answerDto){
        try {
            Question question = questionRepository.findAllById(questionId).orElseThrow(
                    () -> new RuntimeException("해당 게시글이 존재하지 않습니다.")
            );
            Answer answer = new Answer();
            answer.setContent(answerDto.getContent());
            answer.setCreateTime(LocalDateTime.now());
            answer.setQuestion(answerDto.getQuestion());
            answer.setAuthor(answerDto.getAuthor());
            this.answerRepository.save(answer);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }
    // 답변 수정
    public boolean modifyAnswer(Long questionId, AnswerDto answerDto){
        try{
            Answer answer = new Answer();
            answer.setContent(answerDto.getContent());
            answer.setModifyTime(LocalDateTime.now());
            this.answerRepository.save(answer);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }
    // 답변 삭제
    public boolean deleteAnswer(Long answerId) {
        try {
            Answer answer = answerRepository.findById(answerId).orElseThrow(
                    () -> new RuntimeException("해당 답변이 존재하지 않습니다.")
            );
            answerRepository.delete(answer);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }
    public Answer getAnswer(Long id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }


}
