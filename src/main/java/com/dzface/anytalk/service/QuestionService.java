package com.dzface.anytalk.service;

import com.dzface.anytalk.entity.Question;
import com.dzface.anytalk.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public Optional<Question> getQuestion(Long id) {
        return this.questionRepository.findAllById(id);
    }
    public void createQuestion(String title, String content) {
        Question q =new Question();
        q.setTitle(title);
        q.setContent(content);
        q.setCreateTime(LocalDateTime.now());
        this.questionRepository.save(q);
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
    public Page<Question> getPageList(int page){
        //리스트 정렬
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createTime"));
        //PageRequest.of(페이지번호, 한페이지당 게시물 갯수)
        Pageable pageable = PageRequest.of(page, 10);
        return this.questionRepository.findAll(pageable);
    }


}
