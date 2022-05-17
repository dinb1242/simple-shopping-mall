package com.shoppingmall.auth.dto.response;

import com.shoppingmall.auth.enums.LostUsernameTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LostUsernameResponseDto {

    @ApiModelProperty(value = "검색 타입")
    private LostUsernameTypeEnum type;

    @ApiModelProperty(value = "검색 키워드")
    private String answer;

    @ApiModelProperty(value = "검색된 계정")
    private String foundUsername;

}
