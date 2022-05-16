package com.shoppingmall.auth.service;

import com.shoppingmall.auth.dto.request.SignInRequestDto;
import com.shoppingmall.auth.dto.request.SignUpRequestDto;
import com.shoppingmall.user.dto.response.UserDetailsImpl;

public interface AuthService {
    void signIn(SignInRequestDto requestDto) throws Exception;
    UserDetailsImpl signUp(SignUpRequestDto requestDto) throws Exception;
}
