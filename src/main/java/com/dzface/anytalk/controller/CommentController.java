package com.dzface.anytalk.controller;

import com.dzface.anytalk.dto.AnswerDto;
import com.dzface.anytalk.dto.CommentDto;
import com.dzface.anytalk.entity.Comment;
import com.dzface.anytalk.entity.Question;
import com.dzface.anytalk.service.AnswerService;
import com.dzface.anytalk.service.CommentService;
import com.dzface.anytalk.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    // 댓글 생성
    @PostMapping("/create-comment/{id}")
    public ResponseEntity<Boolean> createComment(@PathVariable("id") Long answerId, @RequestBody CommentDto commentDto) {
        boolean isTrue = commentService.createComment(answerId, commentDto);
        return ResponseEntity.ok(isTrue);
    }
//    // 댓글 수정
//    @PutMapping("/modify-comment/{id}")
//    public ResponseEntity<Boolean> modifyCommment(@PathVariable Long answerId, @RequestBody CommentDto commentDto) {
//        boolean isTrue = commentService.modifyComment(answerId, commentDto);
//        return ResponseEntity.ok(isTrue);
//    }
//    // 댓글 삭제
//    @DeleteMapping("/delete-comment/{id}")
//    public  ResponseEntity<Boolean> deleteCommment(@PathVariable Long answerId, @RequestBody CommentDto commentDto) {
//        boolean isTrue = commentService.delectComment(answerId, commentDto);
//        return ResponseEntity.ok(isTrue);
//    }



}
