//AnswerService.java
package com.dzface.anytalk.service;


import com.dzface.anytalk.dto.AnswerDto;
import com.dzface.anytalk.entity.Answer;
import com.dzface.anytalk.entity.Question;
import com.dzface.anytalk.entity.SiteUser;
import com.dzface.anytalk.errorhandler.DataNotFoundException;
import com.dzface.anytalk.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    public Answer createAnswer(AnswerDto answerDto){
        Answer answer = new Answer();
        answer.setContent(answerDto.getContent());
        answer.setCreateTime(LocalDateTime.now());
        answer.setQuestion(answerDto.getQuestion());
        answer.setAuthor(answerDto.getAuthor());
        this.answerRepository.save(answer);
        return answer;
    }
    public Answer getAnswer(Long id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public void modifyAnswer(AnswerDto answerDto, String content) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setModifyTime(LocalDateTime.now());
        this.answerRepository.save(answer);
    }
    public void deleteAnswer(Answer answer) {
        this.answerRepository.delete(answer);
    }
}
