package com.shoppingmall.boot.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestResponseDto<T> {

    private Boolean success;
    private Integer status;
    private String message;
    private T data;

}
