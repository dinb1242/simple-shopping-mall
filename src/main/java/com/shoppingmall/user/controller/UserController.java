package com.shoppingmall.user.controller;

import com.shoppingmall.user.dto.request.UserInfoUpdateRequestDto;
import com.shoppingmall.user.dto.request.UserPasswordUpdateRequestDto;
import com.shoppingmall.user.dto.response.UserDetailsImpl;
import com.shoppingmall.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"[App & 관리자] 유저 API"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/with-auth")
    @ApiOperation(value = "[App] 유저 정보 조회 API", notes = "Authorization 헤더를 통해 유저 Access Token 을 전달받아 해당하는 유저의 정보를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공", response = UserDetailsImpl.class)
    })
    public ResponseEntity<UserDetailsImpl> findUserInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) throws Exception {
        return new ResponseEntity<>(userService.findUserInfo(), HttpStatus.OK);
    }

    @PutMapping("/with-auth/upd/info")
    @ApiOperation(value = "[App] 유저 정보 변경 API", notes = "Authorization 헤더를 통해 유저의 정보를 수정한다. 이때 전달받는 데이터는 DTO 이다.")
    public ResponseEntity<?> updateUserInfo(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @RequestBody UserInfoUpdateRequestDto requestDto
            ) throws Exception {
        return null;
    }

    @PutMapping("/with-auth/upd/pw")
    @ApiOperation(value = "[App] 유저 비밀번호 변경 API", notes = "Authorization 헤더를 통해 유저 Access Token 을 전달받고, 패스워드 변경을 위한 DTO 를 전달받아 해당 유저의 비밀번호를 변경한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "변경 성공", response = Boolean.class),
            @ApiResponse(code = 400, message = "변경 실패 - 기존패스워드 불일치"),
            @ApiResponse(code = 404, message = "변경 실패 - 유저 조회 실패")
    })
    public ResponseEntity<Boolean> updatePassword(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @RequestBody UserPasswordUpdateRequestDto requestDto) throws Exception {
        return new ResponseEntity<>(userService.updatePassword(requestDto), HttpStatus.OK);
    }

}
