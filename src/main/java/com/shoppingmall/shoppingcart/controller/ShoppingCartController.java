package com.shoppingmall.shoppingcart.controller;

import com.shoppingmall.shoppingcart.dto.request.ShoppingCartSaveRequestDto;
import com.shoppingmall.shoppingcart.dto.request.ShoppingCartUpdateRequestDto;
import com.shoppingmall.shoppingcart.dto.response.ShoppingCartResponseDto;
import com.shoppingmall.shoppingcart.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"[App] 장바구니 API"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @PostMapping("")
    @ApiOperation(value = "[App] 장바구니 등록 API", notes = "상품코드와 구매 수량을 DTO 로 전달받아 해당하는 사용자의 장바구니에 아이템을 추가한다.\n만일, 동일한 상품이 이미 장바구니에 있을 경우, 덮어씌우기 한다.")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(code = 201, message = "등록 성공", response = ShoppingCartResponseDto.class),
            @ApiResponse(code = 404, message = "등록 실패 - productCode 미조회")
    })
    public ResponseEntity<ShoppingCartResponseDto> createShoppingCart(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @RequestBody ShoppingCartSaveRequestDto requestDto
            ) throws Exception {
        return new ResponseEntity<>(shoppingCartService.createShoppingCart(requestDto), HttpStatus.CREATED);
    }

    @GetMapping("")
    @ApiOperation(value = "[App] 장바구니 전체 조회 API", notes = "해당하는 유저의 전체 장바구니 상품 목록을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공", response = ShoppingCartResponseDto.class, responseContainer = "List")
    })
    public ResponseEntity<List<ShoppingCartResponseDto>> findAllShoppingCarts(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization
    ) throws Exception {
        return new ResponseEntity<>(shoppingCartService.findAllShoppingCarts(), HttpStatus.OK);
    }

    @PutMapping("/{shoppingCartId}")
    @ApiOperation(value = "[App] 장바구니 수정 API", notes = "장바구니의 특정 시퀀스를 PathVariable 로 전달받아 해당하는 장바구니를 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "수정 성공", response = ShoppingCartResponseDto.class),
            @ApiResponse(code = 404, message = "수정 실패 - shoppingCartId 미조회")
    })
    public ResponseEntity<ShoppingCartResponseDto> updateShoppingCart(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @PathVariable("shoppingCartId") Long shoppingCartId,
            @RequestBody ShoppingCartUpdateRequestDto requestDto
            ) throws Exception {
        return new ResponseEntity<>(shoppingCartService.updateShoppingCart(shoppingCartId, requestDto), HttpStatus.OK);
    }

    @DeleteMapping("/{shoppingCartId}")
    @ApiOperation(value = "[App] 장바구니 삭제 API", notes = "장바구니의 특정 시퀀스를 PathVariable 로 전달받아 해당하는 장바구니를 삭제한다.")
    @ApiResponses( {
            @ApiResponse(code = 200, message = "삭제 성공", response = ShoppingCartResponseDto.class),
            @ApiResponse(code = 404, message = "삭제 실패 - shoppingCartId 미조회")
    })
    public ResponseEntity<ShoppingCartResponseDto> deleteShoppingCart(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @PathVariable("shoppingCartId") Long shoppingCartId
    ) throws Exception {
        return new ResponseEntity<>(shoppingCartService.deleteShoppingCart(shoppingCartId), HttpStatus.OK);
    }

}
