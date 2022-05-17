package com.shoppingmall.user.service;

import com.shoppingmall.user.dto.request.UserInfoUpdateRequestDto;
import com.shoppingmall.user.dto.request.UserPasswordUpdateRequestDto;
import com.shoppingmall.user.dto.response.UserDetailsImpl;

public interface UserService {
    UserDetailsImpl findUserInfo() throws Exception;
    UserDetailsImpl updateUserInfo(UserInfoUpdateRequestDto requestDto) throws Exception;
    Boolean updatePassword(UserPasswordUpdateRequestDto requestDto) throws Exception;
}
