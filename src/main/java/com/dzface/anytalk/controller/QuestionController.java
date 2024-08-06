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

    //게시글 작성
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create-question")
    public ResponseEntity<?> createQuestion(
            @RequestBody @Valid QuestionDto questionDto,
            BindingResult bindingResult,
            Principal principal) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.createQuestion(questionDto, siteUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("Question created successfully");
    }

    //게시글 리스트 조회
    @GetMapping("/question-list")
    public String getQuestionList(Model model){
        List<Question> questionList = this.questionService.getQuestionList();
        //model.addAttribute(String name, Object vlaue) value 객체를 name 이름으로 추가해줌 view 에서 사용
        model.addAttribute("questionList", questionList);
        return "question_list";
    }
    // 페이지 리스트 조회
    @GetMapping("/page-list")
    public String pageList(Model model, @RequestParam(value="page", defaultValue = "0") int page){
        Page<Question> paging = this.questionService.getPageList(page);
        model.addAttribute("paging", paging);
        return "question_list";
    }
    // 상세 게시글 정보 조회
    @GetMapping(value = "/question-detail/{id}")
    public String getQuestionInfo(Model model, @PathVariable("id") Long id) throws DataFormatException {
        Question question = this.questionService.getQuestionInfo(id);
        model.addAttribute("question", question);
        return "question_detail";

    }
    // 게시글 수정
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify-question/{id}")
    public String modifyQuestion(
            @Valid QuestionDto questionDto,
            BindingResult bindingResult,
            @PathVariable("id") Long id,
            Principal principal) throws DataFormatException {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Question q = this.questionService.getQuestionInfo(id);
        if(!q.getAuthor().getName().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.questionService.modifyQuestion(questionDto);
        questionDto.setTitle(q.getTitle());
        questionDto.setContent(q.getContent());
        return "question_form";

    }
    // 게시글 삭제
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete-question/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Long id) throws DataFormatException {
        Question question = this.questionService.getQuestionInfo(id);
        if (!question.getAuthor().getName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.questionService.deleteQuestion(question);
        return "redirect:/";
    }
    // 루트 주소를 질문리스트 출력 게시판주소로 설정(localhost:8118/question/list)
    @GetMapping("/")
    public String root(){
        return "redirect:/support/question-list";
    }
}
