package com.dzface.anytalk.controller;


import com.dzface.anytalk.entity.Question;
import com.dzface.anytalk.service.AnswerService;
import com.dzface.anytalk.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.zip.DataFormatException;

@RequestMapping("/support")
@RequiredArgsConstructor
@Controller
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;
    @PostMapping("/create-answer/{id}")
    public String createAnswer(@PathVariable("id") Long id, @RequestParam(value="content") String content) throws DataFormatException {
        Question question =this.questionService.getQuestionInfo(id);
        this.answerService.createAnswer(question, content);
        return String.format("redirect:/question/detail/%s", id);
    }
}
