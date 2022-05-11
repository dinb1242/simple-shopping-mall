package com.shoppingmall.boot.advisor;

import com.shoppingmall.boot.dto.RestResponseDto;
import com.shoppingmall.boot.exception.RestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler  {

    public RestResponseDto makeRestResponseDto(Integer status, String message) {
        return RestResponseDto.builder()
                .success(false)
                .status(status)
                .message(message)
                .data(new ArrayList<>())
                .build();
    }

    @ExceptionHandler(RestException.class)
    public ResponseEntity<?> restExceptionHandler(RestException e) {
        RestResponseDto responseDto = makeRestResponseDto(e.getHttpStatus().value(), e.getMessage());
        return new ResponseEntity<>(responseDto, e.getHttpStatus());
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<?> internalServerErrorHandler(HttpServerErrorException.InternalServerError e) {
        RestResponseDto responseDto = makeRestResponseDto(e.getStatusCode().value(), e.getMessage());
        return new ResponseEntity<>(responseDto, e.getStatusCode());
    }

}
