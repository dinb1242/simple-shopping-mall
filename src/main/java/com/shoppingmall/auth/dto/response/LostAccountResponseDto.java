package com.shoppingmall.auth.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LostAccountResponseDto {

    @ApiModelProperty(value = "아이디 목록")
    List<String> usernames;

}
