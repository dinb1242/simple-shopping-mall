package com.shoppingmall.auth.controller;

import com.shoppingmall.auth.dto.request.*;
import com.shoppingmall.auth.dto.response.LostPasswordValResponseDto;
import com.shoppingmall.auth.dto.response.LostUsernameResponseDto;
import com.shoppingmall.auth.dto.response.SignInResponseDto;
import com.shoppingmall.auth.service.AuthService;
import com.shoppingmall.user.dto.response.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"[App & 관리자] 인증 API"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authservice;

    @PostMapping("/sign-in")
    @ApiOperation(value = "[App & 관리자] 로그인 API", notes = "로그인을 수행한다. 만일 로그인에 실패할 경우 401 에러가 반환된다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 성공", response = SignInResponseDto.class),
            @ApiResponse(code = 401, message = "로그인 실패")
    })
    public ResponseEntity<SignInResponseDto> signIn(@RequestBody SignInRequestDto requestDto) throws Exception {
        return new ResponseEntity<>(authservice.signIn(requestDto), HttpStatus.OK);
    }

    @PostMapping("/sign-out")
    @ApiOperation(value = "[App & 관리자] 로그아웃 API", notes = "로그아웃을 수행한다. Security Context 에서 해당 정보를 모두 제거하고, DB 내 Auth Token 데이터를 지운다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그아웃 성공", response = UserDetailsImpl.class)
    })
    public ResponseEntity<Boolean> signOut(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) throws Exception {
        return new ResponseEntity<>(authservice.signOut(), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    @ApiOperation(value = "[App & 관리자] 일반 회원가입 API", notes = "DTO 를 전달받아 회원가입을 수행한다. 이때, 기본 권한은 일반 유저로 등록된다.<br/>휴대폰 번호는 반드시 하이픈(-)이 제외되어야 한다.")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(code = 201, message = "생성 성공", response = UserDetailsImpl.class),
            @ApiResponse(code = 400, message = "생성 실패 - 기존재 아이디 혹은 휴대폰 번호")
    })
    public ResponseEntity<UserDetailsImpl> signUp(@RequestBody SignUpRequestDto requestDto) throws Exception {
        return new ResponseEntity<>(authservice.signUp(requestDto), HttpStatus.CREATED);
    }

    @PostMapping("/lost/username")
    @ApiOperation(value = "[App] 아이디 찾기", notes = "이메일 혹은 휴대폰 번호로 찾기를 통해 아이디를 찾는다. 이때 전달받는 데이터는 DTO 이다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "찾기 성공", response = LostUsernameResponseDto.class)
    })
    public ResponseEntity<LostUsernameResponseDto> lostUsername(@RequestBody LostUsernameRequestDto requestDto) throws Exception {
        return new ResponseEntity<>(authservice.lostUsername(requestDto), HttpStatus.OK);
    }

    @PostMapping("/lost/password/val")
    @ApiOperation(value = "[App] 비밀번호 분실 -> 검증 [Step 1]", notes = "분실한 비밀번호를 변경하기 위해 1차적 검증을 수행한다. 검증에 성공했을 경우 유저 시퀀스를 반환한다. 비밀번호 찾기 타입은 Enum 참고")
    @ApiResponses({
            @ApiResponse(code = 200, message = "검증 완료", response = LostPasswordValResponseDto.class),
            @ApiResponse(code = 400, message = "검증 실패"),
            @ApiResponse(code = 404, message = "검증 실패 - 유저 조회 실패")
    })
    public ResponseEntity<LostPasswordValResponseDto> lostPasswordVal(@RequestBody LostPasswordValRequestDto requestDto) throws Exception {
        return new ResponseEntity<>(authservice.lostPasswordVal(requestDto), HttpStatus.OK);
    }

    @PostMapping("/lost/password/upd")
    @ApiOperation(value = "[App] 비밀번호 분실 -> 변경 [Step 2]", notes = "비밀번호 분실 검증 이후, 전달된 유저 시퀀스, 클라이언트에서 변경하기를 희망하는 비밀번호를 DTO 로 전달받아 변경을 수행한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "변경 성공", response = Boolean.class),
            @ApiResponse(code = 404, message = "변경 실패 - 유저 시퀀스 조회 실패")
    })
    public ResponseEntity<Boolean> lostPassword(@RequestBody LostPasswordRequestDto requestDto) throws Exception {
        return new ResponseEntity<>(authservice.lostPassword(requestDto), HttpStatus.OK);
    }
}
