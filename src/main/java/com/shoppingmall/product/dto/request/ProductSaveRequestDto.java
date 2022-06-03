package com.shoppingmall.product.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shoppingmall.file.domain.model.FileEntity;
import com.shoppingmall.product.domain.model.Product;
import com.shoppingmall.product.enums.ProductTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ProductSaveRequestDto {

    @ApiModelProperty(value = "상품명")
    private String productName;

    @ApiModelProperty(value = "상품코드")
    private String productCode;

    @ApiModelProperty(value = "상품정보 - 내용")
    private String productInfo;

    @ApiModelProperty(value = "상품금액")
    private Long productPrice;

    @ApiModelProperty(value = "상품타입", allowableValues = "TYPE_BEST, TYPE_TECH", example = "TYPE_BEST 또는 TYPE_TECH")
    private ProductTypeEnum productType;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private FileEntity thumbnailFile;

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public void setProductPrice(Long productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductType(ProductTypeEnum productType) {
        this.productType = productType;
    }

    public void enrollThumbnailFile(FileEntity thumbnailFile) {
        this.thumbnailFile = thumbnailFile;
    }

    public Product toEntity() {
        return Product.builder()
                .productName(productName)
                .productCode(productCode)
                .productInfo(productInfo)
                .productPrice(productPrice)
                .productType(productType)
                .thumbnailFile(thumbnailFile)
                .build();
    }

}
