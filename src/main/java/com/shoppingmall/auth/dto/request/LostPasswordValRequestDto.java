package com.shoppingmall.auth.dto.request;

import com.shoppingmall.auth.enums.LostPasswordTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LostPasswordValRequestDto {

    @ApiModelProperty("비밀번호 찾기 타입")
    private LostPasswordTypeEnum type;

    @ApiModelProperty("유저 아이디")
    private String username;

    @ApiModelProperty("이름, 이메일 혹은 휴대번호")
    private String answer;

}
