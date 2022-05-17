package com.shoppingmall.auth.dto.request;

import com.shoppingmall.auth.enums.LostUsernameTypeEnum;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LostUsernameRequestDto {

    private LostUsernameTypeEnum type;
    private String answer;

}
