package com.shoppingmall.product.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ProductSaveRequestDto {

    @ApiModelProperty(value = "상품명")
    private String productName;

    @ApiModelProperty(value = "상품번호")
    private String productCode;

    @ApiModelProperty(value = "상품정보")
    private String productInfo;

    @ApiModelProperty(value = "상품금액")
    private Long productPrice;

}
