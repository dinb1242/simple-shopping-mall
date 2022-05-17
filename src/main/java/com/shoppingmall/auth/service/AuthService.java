package com.shoppingmall.auth.service;

import com.shoppingmall.auth.dto.request.*;
import com.shoppingmall.auth.dto.response.LostPasswordValResponseDto;
import com.shoppingmall.auth.dto.response.LostUsernameResponseDto;
import com.shoppingmall.auth.dto.response.SignInResponseDto;
import com.shoppingmall.user.dto.response.UserDetailsImpl;

public interface AuthService {
    SignInResponseDto signIn(SignInRequestDto requestDto) throws Exception;
    Boolean signOut() throws Exception;
    UserDetailsImpl signUp(SignUpRequestDto requestDto) throws Exception;
    LostUsernameResponseDto lostUsername(LostUsernameRequestDto requestDto) throws Exception;
    LostPasswordValResponseDto lostPasswordVal(LostPasswordValRequestDto requestDto) throws Exception;
    Boolean lostPassword(LostPasswordRequestDto requestDto) throws Exception;
}
