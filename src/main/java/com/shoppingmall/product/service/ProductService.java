package com.shoppingmall.product.service;

import com.shoppingmall.product.dto.request.ProductSaveRequestDto;
import com.shoppingmall.product.dto.request.ProductUpdateRequestDto;
import com.shoppingmall.product.dto.response.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    ProductResponseDto createProduct(MultipartFile thumbnailFile, ProductSaveRequestDto requestDto) throws Exception;
    ProductResponseDto updateProduct(Long productId, ProductUpdateRequestDto requestDto) throws Exception;
    Page<ProductResponseDto> findAllProduct(Integer page, Integer elementCnt) throws Exception;
    ProductResponseDto findProduct(Long productId) throws Exception;
    ProductResponseDto deleteProduct(Long productId) throws Exception;
}
