package com.shoppingmall.product.domain.model;

import com.shoppingmall.boot.domain.BaseEntity;
import com.shoppingmall.file.domain.model.File;
import com.shoppingmall.product.dto.request.ProductUpdateRequestDto;
import com.shoppingmall.product.enums.ProductTypeEnum;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_product")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(255) comment '상품명'")
    private String productName;

    @Column(columnDefinition = "varchar(255) comment '상품번호'")
    private String productCode;

    @Column(columnDefinition = "varchar(1000) comment '상품 정보'")
    private String productInfo;

    @Column(columnDefinition = "bigint comment '상품금액(원)'")
    private Long productPrice;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(100) comment '상품 카테고리'")
    private ProductTypeEnum productType;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "tb_product_file",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    private File thumbnailFile;

    /**
     * 영속성 컨텍스트를 활용하여
     * 상품 엔티티를 수정한다.
     * @param requestDto
     */
    public void update(ProductUpdateRequestDto requestDto) {
        if (requestDto.getProductName() != null)
            this.productName = requestDto.getProductName();
        if (requestDto.getProductCode() != null)
            this.productCode = requestDto.getProductCode();
        if (requestDto.getProductInfo() != null)
            this.productInfo = requestDto.getProductInfo();
        if (requestDto.getProductPrice() != null)
            this.productPrice = requestDto.getProductPrice();
    }

    /**
     * 영속성 컨텍스트를 통해 status 값을 삭제를 의미하는 -1 로 변경한다.
     */
    public void delete() {
        setStatus(-1);
    }

}
