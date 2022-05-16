package com.shoppingmall.product.domain.repository;

import com.shoppingmall.product.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Boolean existsByProductCode(String productCode);
}
