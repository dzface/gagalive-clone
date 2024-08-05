package com.dzface.anytalk.service;

import com.dzface.anytalk.entity.Comment;
import com.dzface.anytalk.entity.Question;
import com.dzface.anytalk.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Comment createComment(Question question, String content) {
        Comment c = new Comment();
        c.setContent(content);
        c.setCreateTime(LocalDateTime.now());
        c.setQuestion(question);
        c = this.commentRepository.save(c);
        return c;
    }
    public Optional<Comment> getComment(Long id){
        return this.commentRepository.findById(id);
    }
    public Comment modifyComment(Comment c, String content){
        c.setContent(content);
        c.setModifyTime(LocalDateTime.now());
        return c;
    }
    public  void delectComment(Comment c) {
        this.commentRepository.delete(c);
    }

}
