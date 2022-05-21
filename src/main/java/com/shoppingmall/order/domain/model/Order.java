package com.shoppingmall.order.domain.model;

import com.shoppingmall.boot.domain.BaseEntity;
import com.shoppingmall.order.enums.OrderTypeEnum;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Table(name = "tb_order")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "bigint comment '구매회원 시퀀스'")
    private Long userId;

    @Column(columnDefinition = "varchar(255) comment '주문 번호'")
    private String orderNumber;

    @Column(columnDefinition = "varchar(255) comment '상품 번호'")
    private String productCode;

    @Column(columnDefinition = "int comment '구매 수량'")
    private Integer productCnt;

    @Column(columnDefinition = "bigint comment '총 구매액'")
    private Long price;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(100) comment '결제 방법'")
    private OrderTypeEnum orderType;

    @Column(columnDefinition = "varchar(100) comment '받는 사람 이름'")
    private String receiverName;

    @Column(columnDefinition = "varchar(255) comment '받는 사람 주소'")
    private String receiverAddress;

    @Column(columnDefinition = "varchar(50) comment '받는 사람 휴대번호'")
    private String receiverPhone;

}
