package com.shoppingmall.order.service;

import com.shoppingmall.order.dto.request.OrderSaveRequestDto;
import com.shoppingmall.order.dto.response.OrderResponseDto;

import java.util.List;

public interface OrderService {

    OrderResponseDto createOrder(OrderSaveRequestDto requestDto) throws Exception;
    List<OrderResponseDto> findAllOrders() throws Exception;
    OrderResponseDto findOrder(Long orderId) throws Exception;
    OrderResponseDto deleteOrder(Long orderId) throws Exception;

}
