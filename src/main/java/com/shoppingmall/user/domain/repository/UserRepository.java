package com.shoppingmall.user.domain.repository;

import com.shoppingmall.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findAllByName(String name);
    List<User> findAllByPhone(String phone);
    Boolean existsByUsername(String username);
    Boolean existsByPhone(String phone);
}
