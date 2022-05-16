package com.shoppingmall.auth.service.impl;

import com.shoppingmall.auth.domain.model.Roles;
import com.shoppingmall.auth.domain.repository.RolesRepository;
import com.shoppingmall.auth.dto.request.SignInRequestDto;
import com.shoppingmall.auth.dto.request.SignUpRequestDto;
import com.shoppingmall.auth.dto.response.SignInResponseDto;
import com.shoppingmall.auth.roles.ERole;
import com.shoppingmall.auth.service.AuthService;
import com.shoppingmall.boot.exception.RestException;
import com.shoppingmall.boot.utils.JwtUtils;
import com.shoppingmall.user.domain.model.User;
import com.shoppingmall.user.domain.repository.UserRepository;
import com.shoppingmall.user.dto.response.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    @Transactional
    public SignInResponseDto signIn(SignInRequestDto requestDto) throws Exception {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String token = jwtUtils.generateJwtToken(authentication);

        return SignInResponseDto.builder()
                .userId(userDetails.getId())
                .aT(token)
                .build();
    }

    @Override
    @Transactional
    public UserDetailsImpl signUp(SignUpRequestDto requestDto) throws Exception {
        // 현재 존재하는 회원인지 확인한다.
        if(userRepository.existsByUsername(requestDto.getUsername()))
            throw new RestException(HttpStatus.BAD_REQUEST, "이미 존재하는 유저 아이디입니다. username=" + requestDto.getUsername());

        // 현재 존재하는 휴대폰 번호인지 확인한다.
        if(userRepository.existsByPhone(requestDto.getPhone()))
            throw  new RestException(HttpStatus.BAD_REQUEST, "이미 존재하는 휴대폰 번호입니다. phone=" + requestDto.getPhone());

        // 기본 유저 권한을 추가한다.
        List<Roles> roles = new ArrayList<>();
        Roles userRole = rolesRepository.findByRole(ERole.ROLE_USER)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "일치하는 권한을 찾을 수 없습니다. role=" + ERole.ROLE_USER.name()));
        roles.add(userRole);

        // 유저를 생성한다.
        User user = User.builder()
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .name(requestDto.getName())
                .phone(requestDto.getPhone())
                .email(requestDto.getEmail())
                .address(requestDto.getAddress())
                .roles(roles)
                .build();

        User userEntity = userRepository.save(user);

        return UserDetailsImpl.getUserDetails(userEntity);
    }
}
