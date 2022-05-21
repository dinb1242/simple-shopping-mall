package com.shoppingmall.order.domain.repository;

import com.shoppingmall.order.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
