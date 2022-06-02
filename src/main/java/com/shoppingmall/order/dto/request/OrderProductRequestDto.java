package com.shoppingmall.order.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderProductRequestDto {

    @ApiModelProperty(value = "주문 상품 코드")
    private String productCode;

    @ApiModelProperty(value = "상품 주문 개수")
    private Integer productCnt;

}
