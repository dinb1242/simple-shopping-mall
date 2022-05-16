package com.shoppingmall.product.controller;

import com.shoppingmall.product.dto.request.ProductSaveRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"[App & 관리자] 상품 API"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    @PostMapping("/adm")
    @ApiOperation(value = "[관리자] 상품 등록 API", notes = "관리자 페이지 내에서 Request DTO 를 전달받아 상품 등록을 수행한다.")
    public ResponseEntity<?> createProduct(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @RequestBody ProductSaveRequestDto requestDto
            ) throws Exception {

        return null;
    }

}
