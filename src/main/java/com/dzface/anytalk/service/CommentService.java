package com.dzface.anytalk.service;

import com.dzface.anytalk.dto.CommentDto;
import com.dzface.anytalk.entity.Answer;
import com.dzface.anytalk.entity.Comment;
import com.dzface.anytalk.entity.Question;
import com.dzface.anytalk.repository.AnswerRepository;
import com.dzface.anytalk.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {
    private  final AnswerRepository answerRepository;
    private  final CommentRepository commentRepository;

    // 댓글 생성
    public boolean createComment(Long answerId, CommentDto commentDto){
        try{
            Answer answer = answerRepository.findById(answerId).orElseThrow(
                    () -> new RuntimeException("해당 답변이 존재하지 않습니다.")
            );
            Comment comment = new Comment();
            comment.setContent(commentDto.getContent());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
