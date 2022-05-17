package com.shoppingmall.boot.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomUnauthorizedException extends RuntimeException {

    private HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
    private String message;

}
