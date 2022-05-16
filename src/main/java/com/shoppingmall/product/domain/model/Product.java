package com.shoppingmall.product.domain.model;

import com.shoppingmall.boot.domain.BaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

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

    @Column(columnDefinition = "bigint comment '구매 회원 시퀀스'")
    private Long userId;

}
