package com.study.jwt_imp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.jwt_imp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

}
