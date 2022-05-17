package com.shoppingmall.auth.dto.request;

import com.shoppingmall.auth.enums.LostAccountTypeEnum;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LostAccountRequestDto {

    private LostAccountTypeEnum type;
    private String answer;

}
