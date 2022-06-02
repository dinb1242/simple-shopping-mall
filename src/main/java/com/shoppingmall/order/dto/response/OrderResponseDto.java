package com.shoppingmall.order.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shoppingmall.order.domain.model.Order;
import com.shoppingmall.order.domain.model.OrderProduct;
import com.shoppingmall.order.enums.OrderTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderResponseDto {

    @ApiModelProperty(value = "주문 시퀀스")
    private Long id;

    @ApiModelProperty(value = "구매 회원 시퀀스")
    private Long userId;

    @ApiModelProperty(value = "주문 번호")
    private String orderNumber;

//    @ApiModelProperty(value = "상품 코드")
//    private String productCode;
//
//    @ApiModelProperty(value = "구매 수량")
//    private Integer productCnt;

    @ApiModelProperty(value = "총 구매액")
    private Long price;

    @ApiModelProperty(value = "결제 방법")
    private OrderTypeEnum orderType;

    @ApiModelProperty(value = "받는 사람 이름")
    private String receiverName;

    @ApiModelProperty(value = "받는 사람 주소")
    private String receiverAddress;

    @ApiModelProperty(value = "받는 사람 휴대폰 번호")
    private String receiverPhone;

    @ApiModelProperty(value = "주문 상품 목록")
    private List<OrderProduct> orderProductList;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "주문 일시")
    private LocalDateTime createdAt;

    @Builder
    public OrderResponseDto(Order entity) {
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.orderNumber = entity.getOrderNumber();
//        this.productCode = entity.getProductCode();
//        this.productCnt = entity.getProductCnt();
        this.price = entity.getPrice();
        this.orderType = entity.getOrderType();
        this.receiverName = entity.getReceiverName();
        this.receiverAddress = entity.getReceiverAddress();
        this.receiverPhone = entity.getReceiverPhone();
        this.orderProductList = entity.getOrderProductList();
        this.createdAt = entity.getCreatedAt();
    }

}
