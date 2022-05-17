package com.shoppingmall.user.service.impl;

import com.shoppingmall.boot.exception.RestException;
import com.shoppingmall.user.domain.model.User;
import com.shoppingmall.user.domain.repository.UserRepository;
import com.shoppingmall.user.dto.request.UserInfoUpdateRequestDto;
import com.shoppingmall.user.dto.request.UserPasswordUpdateRequestDto;
import com.shoppingmall.user.dto.response.UserDetailsImpl;
import com.shoppingmall.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 해당 요청은 ROLE_USER 권한을 지닌 사람만 호출이 가능한 API 이다.
     * 권한 정의는 SecurityConfig.java 에서 지정한다.
     * Filter(AuthJwtFilter.java) 에 요청(with Authorization Token, 즉 Access Token 으로 전달받음.)이 유효하다면,
     * Spring Security Context Holder 에 해당 유저의 정보가 삽입되었을 것이므로 해당 Context Holder 를 재활용하여 유저 정보를 추출해낸다.
     * @throws Exception
     */
    @Override
    @Transactional
    public UserDetailsImpl findUserInfo() throws Exception {
        // Security Context Holder 에서 유저 정보를 추출하여 반환한다.
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 유저의 정보를 변경한다.
     * @param requestDto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public UserDetailsImpl updateUserInfo(UserInfoUpdateRequestDto requestDto) throws Exception {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userEntity = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "일치하는 유저를 찾을 수 없습니다. userId=" + userDetails.getId()));

        // 유저의 정보를 변경한다.


        return null;
    }

    /**
     * 유저의 비밀번호를 변경한다.
     * 이때, 클라이언트로부터 입력받은 기존 비밀번호와 DB 내 기존 비밀번호가 일치하는지에 대한 여부를 확인한 후, 변경을 시도한다.
     * @param requestDto
     * @throws Exception
     */
    @Override
    @Transactional
    public Boolean updatePassword(UserPasswordUpdateRequestDto requestDto) throws Exception {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userEntity = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "해당하는 유저 정보가 없습니다. userid=" + userDetails.getId()));

        // 기존 패스워드와 일치하는지 여부를 조회한다.
        if(!passwordEncoder.matches(requestDto.getCurrentPassword(), userEntity.getPassword()))
            throw new RestException(HttpStatus.BAD_REQUEST, "기존 패스워드가 일치하지 않습니다.");

        // 비밀번호를 변경한다.
        userEntity.updatePassword(passwordEncoder.encode(requestDto.getUpdatedPassword()));

        return true;
    }
}
