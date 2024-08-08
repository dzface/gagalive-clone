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
import java.util.*;
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
        SiteUser user = userRepository.findByUserId(questionDto.getUserDto().getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Author not found"));
        try {
            Question question = new Question();
            question.setTitle(questionDto.getTitle());
            question.setContent(questionDto.getContent());
            question.setUser(user);
            questionRepository.save(question);
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
            SiteUser user = userRepository.findByUserId(questionDto.getUserDto().getUserId()).orElseThrow(
                    () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
            );
            question.setTitle(questionDto.getTitle());
            question.setContent(questionDto.getContent());
            question.setModifyTime(String.valueOf(LocalDateTime.now()));
            question.setUser(user);
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
    public Map<String, Object> getQuestionList(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<Question> list = questionRepository.findAll(pageable).getContent();
        List<QuestionDto> listDto = new ArrayList<>();
        for(Question question : list) {
            listDto.add(convertEntityToDto(question));
        }
        int cnt =questionRepository.findAll(pageable).getTotalPages();
        Map<String, Object> result = new HashMap<>();
        result.put("boards", listDto);
        result.put("totalPages", cnt);
        return result;
    }
    public List<QuestionDto> getQuestionList() {
        List<Question> list = questionRepository.findAll();
        List<QuestionDto> listDto = new ArrayList<>();
        for(Question q : list) {
            listDto.add(convertEntityToDto(q));
        }
        return listDto;
    }
    public Page<QuestionDto> getPageList(int page){
        // 정렬 조건 추가
        Sort sort = Sort.by(Sort.Order.desc("createTime"));
        // 페이지 요청 생성 (페이지 번호, 페이지 당 항목 수, 정렬 기준)
        Pageable pageable = PageRequest.of(page, 10, sort);
        // Page<Question>를 가져옴
        Page<Question> questionPage = questionRepository.findAll(pageable);
        // Page<Question>을 Page<QuestionDto>로 변환
        Page<QuestionDto> questionDtoPage = questionPage.map(question -> {
            // 필요한 필드만 포함한 QuestionDto로 변환
            return new QuestionDto();
        });
        return questionDtoPage;
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
        QuestionDto dto = new QuestionDto();
        dto.setId(question.getId());
        dto.setTitle(question.getTitle());
        dto.setContent(question.getContent());
        dto.setUserDto(convertUserToDto(question.getUser()));
        return dto;
    }
    private UserDto convertUserToDto(SiteUser user) {
        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        // 추가 필드들 설정
        return dto;
    }
}
