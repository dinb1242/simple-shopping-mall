package com.shoppingmall.example.dto.request;

import com.shoppingmall.example.domain.model.Example;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExampleSaveRequestDto {

    private String title;

    public Example toEntity() {
        return Example.builder()
                .title(title)
                .build();
    }

}
