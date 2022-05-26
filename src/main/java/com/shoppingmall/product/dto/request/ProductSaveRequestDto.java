package com.shoppingmall.product.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shoppingmall.file.domain.model.File;
import com.shoppingmall.product.domain.model.Product;
import com.shoppingmall.product.enums.ProductTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Getter
@Setter
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

    public Product toEntity() {
        return Product.builder()
                .productName(productName)
                .productCode(productCode)
                .productInfo(productInfo)
                .productPrice(productPrice)
                .productType(productType)
                .build();
    }

}
