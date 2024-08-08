package com.dzface.anytalk.service;

import com.dzface.anytalk.dto.CommentDto;
import com.dzface.anytalk.entity.Comment;
import com.dzface.anytalk.entity.Question;
import com.dzface.anytalk.entity.SiteUser;
import com.dzface.anytalk.repository.CommentRepository;
import com.dzface.anytalk.repository.QuestionRepository;
import com.dzface.anytalk.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    @Transactional
    public CommentDto createComment(Long questionId, String userId, Long parentId, String content) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));
        log.info("Searching for user with commenterId: {}", userId);

        SiteUser commenter = userRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found"));

        Comment parent = parentId != null ? commentRepository.findById(parentId)
                .orElseThrow(() -> new EntityNotFoundException("Parent comment not found")) : null;

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setDeletedStatus(false);
        comment.setQuestion(question);
        comment.setUser(commenter);
        comment.setParent(parent);

        Comment savedComment = commentRepository.save(comment);

        return CommentDto.fromEntity(savedComment);
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByQuestionId(Long questionId) {
        return commentRepository.findByQuestionId(questionId)
                .stream().map(CommentDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CommentDto getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .map(CommentDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
    }
}
