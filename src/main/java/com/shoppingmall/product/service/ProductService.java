package com.shoppingmall.product.service;

import com.shoppingmall.product.dto.request.ProductSaveRequestDto;

public interface ProductService {

    void createProduct(ProductSaveRequestDto requestDto) throws Exception;

}
