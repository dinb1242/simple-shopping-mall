package com.shoppingmall.shoppingcart.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ShoppingCartSaveRequestDto {

    @ApiModelProperty(value = "상품 코드 ")
    private String productCode;

    @ApiModelProperty(value = "구매 수량")
    private Integer productCnt;

}
