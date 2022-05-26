package com.shoppingmall.shoppingcart.domain.repository;

import com.shoppingmall.shoppingcart.domain.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    List<ShoppingCart> findAllByUserId(Long userId);
    Boolean existsByProductCodeAndUserId(String productCode, Long userId);
    Integer deleteByProductCodeAndUserId(String productCode, Long userId);
    Integer deleteByIdAndUserId(Long shoppingCartSeq, Long userId);
}
