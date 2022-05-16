package com.shoppingmall.auth.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInResponseDto {

    @ApiModelProperty(value = "유저 시퀀스")
    private Long userId;

    @ApiModelProperty(value = "유저 aT")
    private String aT;

}
