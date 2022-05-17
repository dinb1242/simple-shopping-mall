package com.shoppingmall.auth.service.impl;

import com.shoppingmall.auth.domain.model.AuthToken;
import com.shoppingmall.auth.domain.model.Roles;
import com.shoppingmall.auth.domain.repository.AuthTokenRepository;
import com.shoppingmall.auth.domain.repository.RolesRepository;
import com.shoppingmall.auth.dto.request.*;
import com.shoppingmall.auth.dto.response.LostPasswordValResponseDto;
import com.shoppingmall.auth.dto.response.LostUsernameResponseDto;
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

    private final AuthTokenRepository authTokenRepository;
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

        /**
         * 로그인 토큰을 DB 에 저장한다.
         * 만일, 이미 중복된 사용자의 기록이 있을 경우 덮어씌우기한다.
         */
        if(authTokenRepository.existsByUserId(userDetails.getId())) {
            log.info("기로그인된 사용자입니다. DB 내 Access Token 갱신을 수행합니다. username=" + userDetails.getUsername());
            AuthToken authTokenEntity = authTokenRepository.findByUserId(userDetails.getId());
            authTokenEntity.updateAccessToken(token);
        } else {
            AuthToken authTokenEntity = AuthToken.builder()
                    .accessToken(token)
                    .userId(userDetails.getId())
                    .build();

            authTokenRepository.save(authTokenEntity);
        }

        return SignInResponseDto.builder()
                .userId(userDetails.getId())
                .accessToken(token)
                .build();
    }

    @Override
    public Boolean signOut() throws Exception {
        // Security Context 를 지운다.
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SecurityContextHolder.clearContext();

        // 기존 DB 에 저장되어있던 데이터를 제거한다.
        authTokenRepository.deleteByUserId(userDetails.getId());

        return true;
    }

    @Override
    @Transactional
    public UserDetailsImpl signUp(SignUpRequestDto requestDto) throws Exception {
        // 현재 존재하는 회원인지 확인한다.
        if(userRepository.existsByUsername(requestDto.getUsername()))
            throw new RestException(HttpStatus.BAD_REQUEST, "이미 존재하는 유저 아이디입니다. username=" + requestDto.getUsername());

        if(userRepository.existsByEmail(requestDto.getEmail()))
            throw new RestException(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다. email=" + requestDto.getEmail());

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

    /**
     * 유저의 이름 혹은 휴대폰 번호를 통해 해당하는 username 을 반환한다.
     * @param requestDto
     * @throws Exception
     */
    @Override
    @Transactional
    public LostUsernameResponseDto lostUsername(LostUsernameRequestDto requestDto) throws Exception {
        String foundUsername = "";

        // 전달받은 Enum 타입에 따라 유저 이름을 검색한다.
        switch (requestDto.getType().name()) {
            case "FIND_BY_EMAIL":
                foundUsername = userRepository.findByEmail(requestDto.getAnswer())
                        .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "일치하는 이메일이 없습니다. email=" + requestDto.getAnswer())).getUsername();
                break;
            case "FIND_BY_PHONE":
                foundUsername = userRepository.findByPhone(requestDto.getAnswer())
                        .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "일치하는 이메일이 없습니다. email=" + requestDto.getAnswer())).getUsername();
                break;
            default :
                throw new RestException(HttpStatus.BAD_REQUEST, "해당하지 않는 타입입니다. type=" + requestDto.getType().name());
        }

        return LostUsernameResponseDto.builder()
                .type(requestDto.getType())
                .answer(requestDto.getAnswer())
                .foundUsername(foundUsername)
                .build();
    }

    /**
     * 비밀번호 변경 전 검증을 수행한다.
     * 검증이 완료될 경우 해당하는 유저의 시퀀스를 리턴한다.
     * 클라이언트는 해당 시퀀스를 전달받은 후, 비밀번호 변경을 수행하며, 추후 전달받은 비밀번호를 통해 비밀번호 변경을 수행한다.
     * @param requestDto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public LostPasswordValResponseDto lostPasswordVal(LostPasswordValRequestDto requestDto) throws Exception {
        // 전달받은 유저 아이디가 존재하는지 여부를 확인한다.
        User userEntity = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "존재하지 않는 계정입니다. username=" + requestDto.getUsername()));

        // 전달받은 타입을 통해 비밀번호 변경 전 검증을 수행한다.
        switch (requestDto.getType().name()) {
            case "SET_WITH_NAME_AND_USERNAME":
                if(!userEntity.getName().equals(requestDto.getAnswer()))
                    throw new RestException(HttpStatus.BAD_REQUEST, String.format("이름이 회원 정보와 일치하지 않습니다. username=%s, name=%s", userEntity.getUsername(), requestDto.getAnswer()));
                break;
            case "SET_WITH_PHONE_AND_USERNAME":
                if(!userEntity.getPhone().equals(requestDto.getAnswer()))
                    throw new RestException(HttpStatus.BAD_REQUEST, String.format("휴대폰 번호가 회원 정보와 일치하지 않습니다. username=%s, phone=%s", userEntity.getUsername(), requestDto.getAnswer()));
                break;
            case "SET_WITH_EMAIL_AND_USERNAME":
                if(!userEntity.getEmail().equals(requestDto.getAnswer()))
                    throw new RestException(HttpStatus.BAD_REQUEST, String.format("이메일이 회원 정보와 일치하지 않습니다. username=%s, email=%s", userEntity.getUsername(), requestDto.getAnswer()));
                break;
            default:
                throw new RestException(HttpStatus.BAD_REQUEST, "해당하지 않는 타입입니다. type=" + requestDto.getType().name());
        }
        // 최종적으로 유저 시퀀스를 반환한다.
        return LostPasswordValResponseDto.builder().userId(userEntity.getId()).build();
    }

    /**
     * 비밀번호 분실 검증 이후 전달받은 DTO 를 통해 비밀번호 변경을 수행한다.
     * @param requestDto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public Boolean lostPassword(LostPasswordRequestDto requestDto) throws Exception {
        User userEntity = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "해당하는 유저를 찾을 수 없습니다. userId=" + requestDto.getUserId()));

        // 비밀번호 변경을 수행한다.
        userEntity.updatePassword(passwordEncoder.encode(requestDto.getUpdatedPassword()));

        return true;
    }
}
