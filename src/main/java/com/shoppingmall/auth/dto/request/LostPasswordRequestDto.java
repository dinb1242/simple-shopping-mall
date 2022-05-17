package com.shoppingmall.auth.dto.request;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LostPasswordRequestDto {

    private Long userId;
    private String updatedPassword;

}
