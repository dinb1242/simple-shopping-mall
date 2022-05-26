package com.shoppingmall.shoppingcart.dto.request;

import com.shoppingmall.shoppingcart.domain.model.ShoppingCart;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ShoppingCartSaveRequestDto {

    @ApiModelProperty(value = "유저 시퀀스", hidden = true)
    private Long userId;

    @ApiModelProperty(value = "상품 코드 ")
    private String productCode;

    @ApiModelProperty(value = "구매 수량")
    private Integer productCnt;

    @ApiModelProperty(value = "상품 총 금액(원)", hidden = true)
    private Long price;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public ShoppingCart toEntity() {
        return ShoppingCart.builder()
                .userId(userId)
                .productCode(productCode)
                .productCnt(productCnt)
                .price(price)
                .build();
    }

}
