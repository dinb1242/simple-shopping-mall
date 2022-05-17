package com.shoppingmall.user.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoUpdateRequestDto {

    @ApiModelProperty(value = "이름")
    private String name;

    @ApiModelProperty(value = "휴대폰 번호")
    private String phone;

    @ApiModelProperty(value = "이메일")
    private String email;

    @ApiModelProperty(value = "주소")
    private String address;

}
