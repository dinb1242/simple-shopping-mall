package com.shoppingmall.order.controller;

import com.shoppingmall.order.dto.request.OrderSaveRequestDto;
import com.shoppingmall.order.dto.response.OrderResponseDto;
import com.shoppingmall.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

@Api(tags = {"[App] 주문 API"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    @ApiOperation(value = "[App] 주문 생성 API", notes = "특정 사용자의 장바구니에 대한 주문을 등록한다.")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(code = 201, message = "주문 생성 성공", response = OrderResponseDto.class),
            @ApiResponse(code = 404, message = "주문 생성 실패 - productCode 조회 실패")
    })
    public ResponseEntity<OrderResponseDto> createOrder(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            OrderSaveRequestDto requestDto
    ) throws Exception {
        return new ResponseEntity<>(orderService.createOrder(requestDto), HttpStatus.CREATED);
    }

}
