package com.shoppingmall.shoppingcart.domain.repository;

import com.shoppingmall.shoppingcart.domain.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}
