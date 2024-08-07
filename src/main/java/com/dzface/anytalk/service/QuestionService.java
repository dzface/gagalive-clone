//QuestionService.java
package com.dzface.anytalk.service;

import com.dzface.anytalk.dto.QuestionDto;
import com.dzface.anytalk.dto.UserDto;
import com.dzface.anytalk.entity.Question;
import com.dzface.anytalk.entity.SiteUser;
import com.dzface.anytalk.repository.QuestionRepository;
import com.dzface.anytalk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Slf4j
@RequiredArgsConstructor // 생성자 생성
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    // 게시글 등록
    @Transactional
    public boolean createQuestion(QuestionDto questionDto) {
        try {
            Question question = createQuestionFromDto(questionDto);
            Question savedQuestion = questionRepository.save(question);
            return true;
        } catch (Exception e) {
            log.error("Error occurred during saveBoard: {}", e.getMessage(), e);
            return false;
        }
    }
    // 게시글 수정
    public boolean modifyQuestion(Long id, QuestionDto questionDto) {
        try {
            Question question = questionRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("해당 게시글이 존재하지 않습니다.")
            );
            SiteUser user = userRepository.findByUserId(questionDto.getAuthor().getUserId()).orElseThrow(
                    () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
            );
            question.setTitle(questionDto.getTitle());
            question.setContent(questionDto.getContent());
            question.setModifyTime(LocalDateTime.now());
            question.setAuthor(user);
            questionRepository.save(question);
            return true;
        } catch (Exception e) {
            log.error("Error occurred during modifyBoard: {}", e.getMessage(), e);
            return false;
        }
    }
    // 게시글 삭제
    public boolean deleteQuestion(Long id) {
        try {
            Question question = questionRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("해당 게시글이 존재하지 않습니다.")
            );
            questionRepository.delete(question);
            return true;
        } catch (Exception e) {
            log.info("Error occurred during deleteBoard: {}", e.getMessage(), e);
            return false;
        }
    }
    // 게시글 전체 조회
    public List<QuestionDto> getQuestionList() {
        List<Question> list = questionRepository.findAll();
        List<QuestionDto> listDto = new ArrayList<>();
        for(Question q : list) {
            listDto.add(convertEntityToDto(q));
        }
        return listDto;
    }
    //페이지네이션
    public Page<Question> getPageList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createTime"));
        //PageRequest.of(페이지번호, 한페이지당 게시물 갯수)
        Pageable pageable = PageRequest.of(page, 10);
        return this.questionRepository.findAll(pageable);
    }
    // 게시글 상세 조회
    public QuestionDto getDetailedQuestion(Long id) {
        Question q = questionRepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 게시글이 존재하지 않습니다.")
        );
        return convertEntityToDto(q);
    }
    // 게시글 제목검색
    public List<QuestionDto> searchBoard(String keyword) {
        List<Question> list = questionRepository.findByTitleContaining(keyword);
        List<QuestionDto> listDto = new ArrayList<>();
        for(Question q : list) {
            listDto.add(convertEntityToDto(q));
        }
        return listDto;
    }
    // 게시글 엔티티를 DTO로 변환
    private QuestionDto convertEntityToDto(Question question) {
        QuestionDto q = new QuestionDto();
        q.setQuestionId(question.getQuestionId());
        q.setTitle(question.getTitle());
        q.setContent(question.getContent());
        q.setCreateTime(question.getCreateTime());
        q.setModifyTime(question.getModifyTime());
        q.setAuthor(question.getAuthor());
        return q;
    }
    private Question createQuestionFromDto(QuestionDto questionDto) {
        SiteUser user = userRepository.findByUserId(questionDto.getAuthor().getUserId()).orElseThrow(
                () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
        );
        Question q = new Question();
        q.setTitle(questionDto.getTitle());
        q.setContent(questionDto.getContent());
        q.setCreateTime(LocalDateTime.now());
        q.setAuthor(questionDto.getAuthor());
        return q;
    }
}
