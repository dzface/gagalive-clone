package com.dzface.anytalk.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class SiteUser {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String userId; // QuestionRepository에서 참조되는 필드
    @Column(unique = true)
    private String name;
    private String password;
    @OneToMany(mappedBy = "author")
    private List<Question> questions;
}
