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

    // 댓글 생성
    @PostMapping("create-comment/{questionId}")
    public ResponseEntity<CommentDto> createComment(@PathVariable("questionId") Long questionId,
                                                    @RequestBody CommentDto commentDto) {
        CommentDto createdComment = commentService.createComment(questionId, commentDto.getUserId(), commentDto.getParentId(), commentDto.getContent());
        return ResponseEntity.ok(createdComment);
    }
    // 댓글 수정
    @PutMapping("/modify-comment/{id}")
    public ResponseEntity<Boolean> modifyComment(@PathVariable("id") Long id, @RequestBody CommentDto commentDto){
        boolean isTure = commentService.modifyComment(id, commentDto);
        return ResponseEntity.ok(isTure);
    }
    // 댓글 삭제 실제로는 삭제되지 않고 DeletedStatus만 true로 변경
    @PutMapping("delete-comment/{id}")
    public ResponseEntity<Boolean> deleteComment(@PathVariable("id") Long id, @RequestBody CommentDto commentDto){
        boolean isTure = commentService.modifyComment(id, commentDto);
        return ResponseEntity.ok(isTure);
    }

    // 댓글 DB에서 리얼루다가 삭제
    @DeleteMapping("delete-physical-comment/{id}")
    public ResponseEntity<Boolean> deletePhysicalComment(@PathVariable("id") Long id) {
        boolean isTure = commentService.deletePhysicalComment(id);
        return ResponseEntity.ok(isTure);
    }
    @GetMapping("/question/{questionId}") ///주소 대소문자 꼭확인 ! @PathVariable 랑 대소문자 안맞추면 적용 안됨
    public ResponseEntity<List<CommentDto>> getCommentsByQuestionId(@PathVariable("questionId") Long questionId) {
        List<CommentDto> comments = commentService.getCommentsByQuestionId(questionId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("commentId") Long commentId) {
        CommentDto comment = commentService.getCommentById(commentId);
        return ResponseEntity.ok(comment);
    }
}
