package com.shoppingmall.user.domain.repository;

import com.shoppingmall.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
    List<User> findAllByName(String name);
    Boolean existsByUsername(String username);
    Boolean existsByPhone(String phone);
    Boolean existsByEmail(String email);
}
