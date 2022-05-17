package com.shoppingmall.auth.service;

import com.shoppingmall.auth.dto.request.LostAccountRequestDto;
import com.shoppingmall.auth.dto.request.SignInRequestDto;
import com.shoppingmall.auth.dto.request.SignUpRequestDto;
import com.shoppingmall.auth.dto.response.SignInResponseDto;
import com.shoppingmall.user.dto.response.UserDetailsImpl;

public interface AuthService {
    SignInResponseDto signIn(SignInRequestDto requestDto) throws Exception;
    UserDetailsImpl signUp(SignUpRequestDto requestDto) throws Exception;
    void lostUsername(LostAccountRequestDto requestDto) throws Exception;
}
