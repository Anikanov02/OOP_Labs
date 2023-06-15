package com.anikanov.repository;

import com.anikanov.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
    User findByUsername(String username);
    User findByPhoneNumber(String phoneNumber);
}
