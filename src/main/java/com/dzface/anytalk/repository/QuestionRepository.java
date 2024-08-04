package com.dzface.anytalk.repository;

import com.dzface.anytalk.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;


// interface 이어야하고 JpaRepository 상속받아야하고, <조회 할 엔터티, 기본키타입>
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
