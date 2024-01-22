package com.jiho.anniehands.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // 이메일로 조회
    Optional<User> findByEmail(String email);
    
    // 아이디로 조회
    Optional<User> findById(String id);

    boolean existsById(String id);

    boolean existsByEmail(String email);

    // 모두 조회
    List<User> findAll();


}
