//CommentController.java
package com.dzface.anytalk.controller;

import com.dzface.anytalk.dto.CommentDto;
import com.dzface.anytalk.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/support")
public class CommentController {
    private final CommentService commentService;


    @PostMapping("create-comment/{id}")
    public ResponseEntity<CommentDto> createComment(@PathVariable("id") Long questionId,
                                                    @RequestBody CommentDto commentDto) {
        CommentDto createdComment = commentService.createComment(questionId, commentDto.getUserId(), commentDto.getParentId(), commentDto.getContent());
        return ResponseEntity.ok(createdComment);
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<CommentDto>> getCommentsByQuestionId(@PathVariable Long questionId) {
        List<CommentDto> comments = commentService.getCommentsByQuestionId(questionId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long commentId) {
        CommentDto comment = commentService.getCommentById(commentId);
        return ResponseEntity.ok(comment);
    }
}
