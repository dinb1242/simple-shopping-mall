package com.shoppingmall.user.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shoppingmall.user.domain.model.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailsImpl implements UserDetails {
    @ApiModelProperty(value = "유저 시퀀스")
    private Long id;
    @ApiModelProperty(value = "유저 아이디")
    private String username;
    @JsonIgnore
    private String password;
    @ApiModelProperty(value = "유저 실명")
    private String name;
    @ApiModelProperty(value = "유저 휴대폰")
    private String phone;
    @ApiModelProperty(value = "유저 이메일")
    private String email;
    @ApiModelProperty(value = "유저 주소")
    private String address;
    @ApiModelProperty(value = "유저 권한")
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl getUserDetails(User entity) {
        List<GrantedAuthority> authorities = entity.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toList());

        return UserDetailsImpl.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .name(entity.getName())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .address(entity.getAddress())
                .authorities(authorities)
                .build();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
