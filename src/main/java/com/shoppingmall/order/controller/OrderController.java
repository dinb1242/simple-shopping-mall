package com.shoppingmall.order.controller;

import com.shoppingmall.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

@Api(tags = {"[App] 주문 API"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

//    private final OrderService orderService;

    @PostMapping("")
    @ApiOperation(value = "[App] 주문 생성 API", notes = "특정 사용자의 장바구니에 대한 주문을 등록한다.")
    public ResponseEntity<?> createOrder(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            HttpServletRequest request
    ) throws Exception {
        return null;
    }

}
