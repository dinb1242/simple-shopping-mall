package com.shoppingmall.example.dto.response;

import com.shoppingmall.example.domain.model.Example;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ExampleResponseDto {

    @ApiModelProperty(value = "시퀀스")
    private Long id;
    @ApiModelProperty(value = "제목")
    private String title;
    @ApiModelProperty(value = "생성일")
    private LocalDateTime createdAt;
    @ApiModelProperty(value = "수정일")
    private LocalDateTime updatedAt;

    @Builder
    public ExampleResponseDto(Example entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }

}
