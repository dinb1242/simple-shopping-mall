package com.shoppingmall.user.service;

import com.shoppingmall.user.dto.request.UserInfoUpdateRequestDto;
import com.shoppingmall.user.dto.request.UserPasswordUpdateRequestDto;
import com.shoppingmall.user.dto.response.UserDetailsImpl;
import com.shoppingmall.user.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {
    UserDetailsImpl findUserInfo() throws Exception;
    UserResponseDto updateUserInfo(UserInfoUpdateRequestDto requestDto) throws Exception;
    Boolean updatePassword(UserPasswordUpdateRequestDto requestDto) throws Exception;

    /**
     * 관리자 권한 API
     */
    List<UserResponseDto> admFindUsers() throws Exception;
    UserResponseDto admDeleteUser(Long userId) throws Exception;
}
