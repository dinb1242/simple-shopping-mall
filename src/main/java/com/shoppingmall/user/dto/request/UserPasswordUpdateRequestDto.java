package com.shoppingmall.user.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPasswordUpdateRequestDto {

    @ApiModelProperty(value = "현재 유저 비밀번호")
    private String currentPassword;
    @ApiModelProperty(value = "변경할 유저 비밀번호")
    private String updatedPassword;

}
