package com.shoppingmall.order.domain.repository;

import com.shoppingmall.order.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserIdAndStatus(Long userId, Integer status);
}
