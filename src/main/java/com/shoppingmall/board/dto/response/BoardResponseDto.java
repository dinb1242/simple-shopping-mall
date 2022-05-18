package com.shoppingmall.board.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shoppingmall.board.domain.model.Board;
import com.shoppingmall.board.enums.BoardTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class BoardResponseDto {

    @ApiModelProperty(value = "시퀀스")
    private Long id;

    @ApiModelProperty(value = "유저 시퀀스")
    private Long userId;

    @ApiModelProperty(value = "타입")
    private BoardTypeEnum type;

    @ApiModelProperty(value = "내용")
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "생성일")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "수정일")
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "상태")
    private Integer status;

    @Builder
    public BoardResponseDto(Board entity) {
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.type = entity.getType();
        this.content = entity.getContent();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.status = entity.getStatus();
    }

}
