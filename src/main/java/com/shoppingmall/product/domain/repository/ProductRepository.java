package com.shoppingmall.product.domain.repository;

import com.shoppingmall.product.domain.model.Product;
import com.shoppingmall.product.domain.repository.custom.ProductRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    Optional<Product> findByIdAndStatus(Long productId, Integer status);
    Optional<Product> findByProductCodeAndStatus(String productCode, Integer status);
    Boolean existsByProductCode(String productCode);
}
