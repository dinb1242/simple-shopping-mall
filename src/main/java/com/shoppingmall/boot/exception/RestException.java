package com.shoppingmall.boot.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;
}
