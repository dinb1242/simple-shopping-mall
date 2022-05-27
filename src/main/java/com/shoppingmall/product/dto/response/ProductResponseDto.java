package com.shoppingmall.product.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shoppingmall.file.domain.model.FileEntity;
import com.shoppingmall.product.domain.model.Product;
import com.shoppingmall.product.enums.ProductTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class ProductResponseDto {

    @ApiModelProperty(value = "")
    private Long id;

    @ApiModelProperty(value = "상품명")
    private String productName;

    @ApiModelProperty(value = "상품번호")
    private String productCode;

    @ApiModelProperty(value = "상품정보")
    private String productInfo;

    @ApiModelProperty(value = "상품 타입")
    private ProductTypeEnum productType;

    @ApiModelProperty(value = "상품금액")
    private Long productPrice;

    @ApiModelProperty(value = "썸네일 파일")
    private FileEntity thumbnailFile;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "생성일")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "수정일")
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "삭제 여부")
    private Integer status;

    @Builder
    public ProductResponseDto(Product entity) {
        this.id = entity.getId();
        this.productName = entity.getProductName();
        this.productCode = entity.getProductCode();
        this.productInfo = entity.getProductInfo();
        this.productType = entity.getProductType();
        this.productPrice = entity.getProductPrice();
        this.thumbnailFile = entity.getThumbnailFile();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.status = entity.getStatus();
    }



}
