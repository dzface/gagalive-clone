//AnswerController.java
package com.dzface.anytalk.controller;


import com.dzface.anytalk.dto.AnswerDto;
import com.dzface.anytalk.dto.QuestionDto;
import com.dzface.anytalk.entity.Answer;
import com.dzface.anytalk.entity.Question;
import com.dzface.anytalk.entity.SiteUser;
import com.dzface.anytalk.service.AnswerService;
import com.dzface.anytalk.service.QuestionService;
import com.dzface.anytalk.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // 답변 등록
    @PostMapping("/create-answer/{id}")
    public ResponseEntity<Boolean> createAnswer(@PathVariable Long Id, AnswerDto answerDto) {
        boolean isTrue = answerService.createAnswer(Id, answerDto);
        return ResponseEntity.ok(isTrue);
    }
    // 답변 수정
    @PutMapping("/modify-answer/{id}")
    public ResponseEntity<Boolean> modifyAnswer(@PathVariable Long Id, @RequestBody AnswerDto answerDto) {
        boolean isTrue = answerService.modifyAnswer(Id, answerDto);
        return ResponseEntity.ok(isTrue);
    }
    // 답변 삭제
    @DeleteMapping("/delete-answer/{id}")
    public  ResponseEntity<Boolean> deleteAnswer(@PathVariable Long Id) {
        boolean isTrue = answerService.deleteAnswer(Id);
        return ResponseEntity.ok(isTrue);
    }
}
