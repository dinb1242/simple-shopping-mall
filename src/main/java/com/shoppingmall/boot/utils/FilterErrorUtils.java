package com.shoppingmall.boot.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingmall.boot.dto.RestResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilterErrorUtils {

    private final ObjectMapper objectMapper;

    public RestResponseDto getExceptionResponseDto(Exception e, Integer status) {
        RestResponseDto responseDto = RestResponseDto.builder()
                .success(false)
                .message(e.getMessage())
                .status(status)
                .data(new ArrayList<>())
                .build();

        return responseDto;
    }

    public HttpServletResponse sendUnauthorizedException(Exception e, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(getExceptionResponseDto(e, HttpServletResponse.SC_UNAUTHORIZED)));

        return response;
    }

    public HttpServletResponse sendForbiddenException(Exception e, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(objectMapper.writeValueAsString(getExceptionResponseDto(e, HttpServletResponse.SC_FORBIDDEN)));

        return response;
    }

    public HttpServletResponse sendAccessTokenExpiredException(Exception e, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.setStatus(999);
        response.getWriter().write(objectMapper.writeValueAsString(getExceptionResponseDto(e, 999)));

        return response;
    }

}
