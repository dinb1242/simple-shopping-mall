package com.shoppingmall.board.dto.request;

import com.shoppingmall.board.domain.model.Board;
import com.shoppingmall.board.enums.BoardTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class BoardSaveRequestDto {

    @ApiModelProperty(value = "타입")
    private BoardTypeEnum type;

    @ApiModelProperty(hidden = true)
    private Long userId;

    @ApiModelProperty(value = "내용")
    private String content;

    /**
     * 비즈니스 로직 내에서 createBoard 메소드 수행 시,
     * Spring Security Context Holder 로부터 전달받은 Principle 에 대한 유저 시퀀스를 삽입하기 위한 Setter
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 전달받은 DTO 를 바탕으로 Repository 에 저장하기 위한 엔티티로 변환하여 반환한다.
     * 빌더 패턴을 활용한다.
     * @return
     */
    public Board toEntity() {
        return Board.builder()
                .type(type)
                .userId(userId)
                .content(content)
                .build();
    }

}
