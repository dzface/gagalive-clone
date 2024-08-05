//AnswerController.java
package com.dzface.anytalk.controller;


import com.dzface.anytalk.dto.AnswerDto;
import com.dzface.anytalk.entity.Answer;
import com.dzface.anytalk.entity.Question;
import com.dzface.anytalk.entity.SiteUser;
import com.dzface.anytalk.service.AnswerService;
import com.dzface.anytalk.service.QuestionService;
import com.dzface.anytalk.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.zip.DataFormatException;

@RequestMapping("/support")
@RequiredArgsConstructor
@Controller
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create-answer/{id}")
    public String createAnswer(
            Model model,
            @PathVariable("id") Long id,
            @RequestParam(value="content") String content,
            @Valid AnswerDto answerDto,
            BindingResult bindingResult,
            Principal principal) throws DataFormatException {
        Question question =this.questionService.getQuestionInfo(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }
        this.answerService.createAnswer(answerDto);
        return String.format("redirect:/question/detail/%s", id);
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify-answer/{id}")
    public String answerModify(@Valid AnswerDto answerDto, BindingResult bindingResult,
                               @PathVariable("id") Long id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "answer_form";
        }
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.answerService.modifyAnswer(answerDto, answerDto.getContent());
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete-answer/{id}")
    public String deleteAnswer(Principal principal, @PathVariable("id") Long id) {
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.answerService.deleteAnswer(answer);
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }
}
