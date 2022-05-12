package com.shoppingmall.example.dto.request;

import com.shoppingmall.example.domain.model.Example;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExampleSaveRequestDto {

    @ApiModelProperty(value = "예제 제목")
    private String title;

    public Example toEntity() {
        return Example.builder()
                .title(title)
                .build();
    }

}
