package com.shoppingmall.shoppingcart.domain.model;

import com.shoppingmall.boot.domain.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Table(name = "tb_shopping_cart")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class ShoppingCart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "bigint comment '유저 시퀀스'")
    private Long userId;

    @Column(columnDefinition = "varchar(255) comment '상품 코드'")
    private String productCode;

    @Column(columnDefinition = "int comment '구매 수량'")
    private Integer productCnt;

    @Column(columnDefinition = "bigint comment '상품 총 금액(원)'")
    private Long price;

}
