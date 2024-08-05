package com.dzface.anytalk.repository;

import com.dzface.anytalk.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
