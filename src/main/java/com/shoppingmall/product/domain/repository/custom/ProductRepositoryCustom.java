package com.shoppingmall.product.domain.repository.custom;

import com.shoppingmall.product.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    Page<Product> findAllWithPaging(Pageable pageable);
}
