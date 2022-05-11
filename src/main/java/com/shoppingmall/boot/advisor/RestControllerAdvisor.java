package com.shoppingmall.boot.advisor;

import com.shoppingmall.boot.dto.RestResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class RestControllerAdvisor implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        String endpoint = returnType.getContainingClass().getSimpleName();
        log.info(endpoint);
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) request;
        Integer status = httpServletResponse.getStatus();

        RestResponseDto responseDto = RestResponseDto.builder()
                .success(true)
                .status(status)
                .message("")
                .data(body)
                .build();

        return responseDto;
    }
}
