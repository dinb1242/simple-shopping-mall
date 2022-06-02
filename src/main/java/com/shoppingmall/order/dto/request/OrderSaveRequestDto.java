package com.shoppingmall.order.dto.request;

import com.shoppingmall.order.domain.model.Order;
import com.shoppingmall.order.enums.OrderTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderSaveRequestDto {

    @ApiModelProperty(value = "결제 방법")
    private OrderTypeEnum orderType;

    @ApiModelProperty(value = "주문 상품 장바구니 DTO 리스트")
    private List<Long> shoppingCartIdList;

    @ApiModelProperty(value = "받는 사람 이름")
    private String receiverName;

    @ApiModelProperty(value = "받는 사람 주소")
    private String receiverAddress;

    @ApiModelProperty(value = "받는 사람 휴대폰 번호")
    private String receiverPhone;

}
