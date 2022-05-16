package com.shoppingmall.auth.controller;

import com.shoppingmall.auth.dto.request.SignInRequestDto;
import com.shoppingmall.auth.dto.request.SignUpRequestDto;
import com.shoppingmall.auth.service.AuthService;
import com.shoppingmall.user.dto.response.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"[App & 관리자] 인증 API"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authservice;

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRequestDto requestDto) throws Exception {
        return new ResponseEntity<>(authservice.signIn(requestDto), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    @ApiOperation(value = "[App & 관리자] 일반 회원가입 API", notes = "DTO 를 전달받아 회원가입을 수행한다. 이때, 기본 권한은 일반 유저로 등록된다.")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(code = 201, message = "생성 성공", response = UserDetailsImpl.class),
            @ApiResponse(code = 400, message = "생성 실패 - 기존재 아이디 혹은 휴대폰 번호")
    })
    public ResponseEntity<UserDetailsImpl> signUp(@RequestBody SignUpRequestDto requestDto) throws Exception {
        return new ResponseEntity<>(authservice.signUp(requestDto), HttpStatus.CREATED);
    }

}
