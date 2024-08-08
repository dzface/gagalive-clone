//QuestionController.java
package com.dzface.anytalk.controller;

import com.dzface.anytalk.dto.QuestionDto;
import com.dzface.anytalk.dto.UserDto;
import com.dzface.anytalk.entity.Question;
import com.dzface.anytalk.entity.SiteUser;
import com.dzface.anytalk.service.QuestionService;
import com.dzface.anytalk.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.zip.DataFormatException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/support")
public class QuestionController {
    private final QuestionService questionService;
    private final UserService userService;

    // 게시글 등록
    @PostMapping("/create-question")
    public ResponseEntity<Boolean> createQuestion(@RequestBody QuestionDto questionDto) {
        boolean isTrue = questionService.createQuestion(questionDto);
        return ResponseEntity.ok(isTrue);
    }
    // 게시글 수정
    @PutMapping("/modify-question/{id}") // id는 수정하려는 게시글의 id @PathVariable("id") Long id 중요
    public ResponseEntity<Boolean> modifyQuestion(@PathVariable("id") Long id, @RequestBody QuestionDto questionDto) {
        boolean isTrue = questionService.modifyQuestion(id, questionDto);
        return ResponseEntity.ok(isTrue);
    }
    // 게시글 삭제
    @DeleteMapping("/delete-question/{id}")
    public ResponseEntity<Boolean> deleteQuestion(@PathVariable("id") Long id) {
        boolean isTrue = questionService.deleteQuestion(id);
        return ResponseEntity.ok(isTrue);
    }
    // 게시글 목록 조회
    @GetMapping("/question-list")
    public ResponseEntity<List<QuestionDto>> getQuestionList() {
        List<QuestionDto> list = questionService.getQuestionList();
        return ResponseEntity.ok(list);
    }
    // 페이지 리스트 조회
    @GetMapping("/page-list")
    public ResponseEntity<Page<Question>> pageList(Model model, @RequestParam(value="page", defaultValue = "0") int page){
        Page<Question> paging = this.questionService.getPageList(page);
        model.addAttribute("paging", paging);
        return ResponseEntity.ok(paging);
    }
    // 게시글 상세 조회
    @GetMapping("/detailed-question/{id}")
    public ResponseEntity<QuestionDto> getBoardDetail(@PathVariable Long id) {
        QuestionDto questionDto = questionService.getDetailedQuestion(id);
        return ResponseEntity.ok(questionDto);
    }


}
