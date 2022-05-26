package com.shoppingmall.shoppingcart.service;

import com.shoppingmall.shoppingcart.dto.request.ShoppingCartSaveRequestDto;
import com.shoppingmall.shoppingcart.dto.request.ShoppingCartUpdateRequestDto;
import com.shoppingmall.shoppingcart.dto.response.ShoppingCartResponseDto;

import java.util.List;

public interface ShoppingCartService {

    ShoppingCartResponseDto createShoppingCart(ShoppingCartSaveRequestDto requestDto) throws Exception;
    List<ShoppingCartResponseDto> findAllShoppingCarts() throws Exception;
    ShoppingCartResponseDto updateShoppingCart(Long shoppingCartId, ShoppingCartUpdateRequestDto requestDto) throws Exception;
    ShoppingCartResponseDto deleteShoppingCart(Long shoppingCartId) throws Exception;

}
