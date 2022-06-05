package com.shoppingmall.shoppingcart.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shoppingmall.shoppingcart.domain.model.ShoppingCart;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class ShoppingCartResponseDto {

    @ApiModelProperty(value = "시퀀스")
    private Long id;

    @ApiModelProperty(value = "유저 시퀀스")
    private Long userId;

    @ApiModelProperty(value = "상품 코드")
    private String productCode;

    @ApiModelProperty(value = "상품명")
    private String productName;

    @ApiModelProperty(value = "구매 수량")
    private Integer productCnt;

    @ApiModelProperty(value = "상품 총 금액(원)")
    private Long price;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "생성일")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "수정일")
    private LocalDateTime updatedAt;

    @Builder
    public ShoppingCartResponseDto(ShoppingCart entity) {
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.productCode = entity.getProductCode();
        this.productCnt = entity.getProductCnt();
        this.productName = entity.getProductName();
        this.price = entity.getPrice();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }

}
