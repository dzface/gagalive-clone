package com.dzface.anytalk.controller;

import com.dzface.anytalk.entity.Comment;
import com.dzface.anytalk.entity.Question;
import com.dzface.anytalk.service.AnswerService;
import com.dzface.anytalk.dto.CommentForm;
import com.dzface.anytalk.service.CommentService;
import com.dzface.anytalk.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Optional;
import java.util.zip.DataFormatException;

public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;
    @GetMapping(value = "/create/question/{id}")
    public String createQuestionComment(CommentForm commentForm) {
        return "comment_form";
    }


    @PostMapping(value = "/create/question/{id}")
    public String createQuestionComment(@PathVariable("id") Long id, @Valid CommentForm commentForm,
                                        BindingResult bindingResult, Principal principal) throws DataFormatException {
        Optional<Question> question = this.questionService.getQuestion(id);
        if (question.isPresent()) {
            if (bindingResult.hasErrors()) {
                return "comment_form";
            }
            Comment c = this.commentService.createComment(question.get(), commentForm.getContent());
            return String.format("redirect:/question/detail/%s", c.getQuestionId());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
    }
    @GetMapping("/modify/{id}")
    public String modifyComment(CommentForm commentForm, @PathVariable("id") Long id, Principal principal) {
        Optional<Comment> comment = this.commentService.getComment(id);
        if (comment.isPresent()) {
            Comment c = comment.get();
            commentForm.setContent(c.getContent());
        }
        return "comment_form";
    }
    @PostMapping("/modify/{id}")
    public String modifyComment(@Valid CommentForm commentForm, BindingResult bindingResult, Principal principal,
                                @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "comment_form";
        }
        Optional<Comment> comment = this.commentService.getComment(id);
        if (comment.isPresent()) {
            Comment c = comment.get();

            c = this.commentService.modifyComment(c, commentForm.getContent());
            return String.format("redirect:/question/detail/%s", c.getQuestionId());

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteComment(Principal principal, @PathVariable("id") Long id) {
        Optional<Comment> comment = this.commentService.getComment(id);
        if (comment.isPresent()) {
            Comment c = comment.get();

            this.commentService.delectComment(c);
            return String.format("redirect:/question/detail/%s", c.getQuestionId());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
    }

}
