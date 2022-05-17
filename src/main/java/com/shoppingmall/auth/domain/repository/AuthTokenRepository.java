package com.shoppingmall.auth.domain.repository;

import com.shoppingmall.auth.domain.model.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
    AuthToken findByUserId(Long userId);
    Integer deleteByUserId(Long userId);
    Boolean existsByAccessToken(String accessToken);
    Boolean existsByUserId(Long userId);
}
