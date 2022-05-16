package com.shoppingmall.auth.filter;

import com.shoppingmall.auth.domain.repository.AuthTokenRepository;
import com.shoppingmall.boot.exception.RestException;
import com.shoppingmall.boot.utils.FilterErrorUtils;
import com.shoppingmall.boot.utils.JwtUtils;
import com.shoppingmall.user.dto.response.UserDetailsImpl;
import com.shoppingmall.user.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthJwtFilter extends OncePerRequestFilter {

    private final AuthTokenRepository authTokenRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtils jwtUtils;
    private final FilterErrorUtils filterErrorUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        try {
            String token = getAccessToken(request);
            String requestPath = request.getServletPath();

            log.info(request.getServletPath());

            // Access 토큰이 유효할 경우
            if(!requestPath.equals("/auth/token/refresh") && !requestPath.equals("/auth/sign-in") && token != null && jwtUtils.validate(token)) {
                // 만일, DB 내에 존재하는 Access Token 과 전달받은 Access Token 이 다를 경우 예외를 발생시킨다.
                if(!authTokenRepository.existsByAccessToken(token)) {
                    throw new RestException(HttpStatus.UNAUTHORIZED, "Access Token 이 DB 내 토큰과 일치하지 않습니다. 이전 사용자/로그아웃된 사용자, 혹은 조작된 토큰일 수 있습니다.");
                }

                // Security Context 에 인증한다.
                String username = jwtUtils.getUsernameFromToken(token);
                UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch(SignatureException e) {
            e.printStackTrace();
            filterErrorUtils.sendUnauthorizedException(e, response);
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            filterErrorUtils.sendAccessTokenExpiredException(e, response);
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
            filterErrorUtils.sendUnauthorizedException(e, response);
        } catch (MalformedJwtException e) {
            e.printStackTrace();
            filterErrorUtils.sendUnauthorizedException(e, response);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            filterErrorUtils.sendUnauthorizedException(e, response);
        }
    }

    public String getAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
            return bearerToken.replace("Bearer ", "");
        return null;
    }
}
