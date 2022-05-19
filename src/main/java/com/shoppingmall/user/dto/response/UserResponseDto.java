package com.shoppingmall.user.dto.response;

import com.shoppingmall.user.domain.model.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class UserResponseDto {

    @ApiModelProperty(value = "시퀀스")
    private Long id;

    @ApiModelProperty(value = "회원 아이디")
    private String username;

    @ApiModelProperty(value = "회원 실명")
    private String name;

    @ApiModelProperty(value = "휴대폰 번호")
    private String phone;

    @ApiModelProperty(value = "이메일")
    private String email;

    @ApiModelProperty(value = "주소")
    private String address;

    @Builder
    public UserResponseDto(User entity) {
        this.id = entity.getId();
        this.username = entity.getUsername();
        this.name = entity.getName();
        this.phone = entity.getPhone();
        this.email = entity.getEmail();
        this.address = entity.getAddress();
    }

}
