//SiteUser.java
package com.dzface.anytalk.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(unique = true, nullable = false)
    private String userId;
    @Column(unique = true)
    private String name;
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Question> questions;
}
