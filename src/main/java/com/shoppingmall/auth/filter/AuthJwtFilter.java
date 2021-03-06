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
import org.springframework.http.server.RequestPath;
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
import java.nio.file.Path;

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

            // Access ????????? ????????? ?????? ??? ??????????????? ????????? /auth ??? ?????? ??????
            if(!Path.of(requestPath).startsWith("/auth/sign-in") && token != null && jwtUtils.validate(token)) {
                // ??????, DB ?????? ???????????? Access Token ??? ???????????? Access Token ??? ?????? ?????? ????????? ???????????????.
                if(!authTokenRepository.existsByAccessToken(token)) {
                    throw new RestException(HttpStatus.UNAUTHORIZED, "Access Token ??? DB ??? ????????? ???????????? ????????????. ?????? ?????????/??????????????? ?????????, ?????? ????????? ????????? ??? ????????????.");
                }

                // Security Context ??? ????????????.
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
        } catch (RestException e) {
            e.printStackTrace();
            filterErrorUtils.sendRestException(e, response);
        }
    }

    public String getAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
            return bearerToken.replace("Bearer ", "");
        return null;
    }
}
