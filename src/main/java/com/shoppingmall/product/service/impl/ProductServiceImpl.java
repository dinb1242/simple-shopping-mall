package com.shoppingmall.product.service.impl;

import com.shoppingmall.product.domain.repository.ProductRepository;
import com.shoppingmall.product.dto.request.ProductSaveRequestDto;
import com.shoppingmall.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    /**
     * 관리자 페이지에서 상품을 등록한다.
     * 이때, 상품 번호가 중복될 경우 예외를 발생시킨다.
     * @param requestDto
     * @throws Exception
     */
    @Override
    public void createProduct(ProductSaveRequestDto requestDto) throws Exception {
        if(productRepository.existsByProductCode(requestDto.getProductCode())) {}
//            throw new Restexception
    }
}
