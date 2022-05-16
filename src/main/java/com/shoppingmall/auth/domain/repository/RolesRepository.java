package com.shoppingmall.auth.domain.repository;

import com.shoppingmall.auth.domain.model.Roles;
import com.shoppingmall.auth.roles.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByRole(ERole role);
}
