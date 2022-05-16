package com.shoppingmall.auth.domain.repository;

import com.shoppingmall.auth.domain.model.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
    Boolean existsByAccessToken(String accessToken);
}
