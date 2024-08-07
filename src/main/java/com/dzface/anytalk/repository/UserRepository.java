package com.dzface.anytalk.repository;

import com.dzface.anytalk.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    Optional<SiteUser> findByUserId(String userId);
    Optional<SiteUser> findByName(String name);
    Optional<SiteUser> findByUserIdAndPassword(String userId, String password);
    boolean existsByUserId(String userId);

}
