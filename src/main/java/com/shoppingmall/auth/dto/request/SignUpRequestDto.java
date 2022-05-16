package com.shoppingmall.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequestDto {

    @ApiModelProperty(value = "유저 아이디")
    private String username;

    @ApiModelProperty(value = "패스워드")
    private String password;

    @ApiModelProperty(value = "회원 실명")
    private String name;

    @ApiModelProperty(value = "휴대폰 번호(- 없이)")
    private String phone;

    @ApiModelProperty(value = "이메일")
    private String email;

    @ApiModelProperty(value = "주소")
    private String address;

    @ApiModelProperty(value = "권한")
    @JsonIgnore
    private List<String> roles;

}
