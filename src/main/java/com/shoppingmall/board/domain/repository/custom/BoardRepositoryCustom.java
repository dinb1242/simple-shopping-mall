package com.shoppingmall.board.domain.repository.custom;

import com.shoppingmall.board.dto.response.BoardResponseDto;

import java.util.List;

public interface BoardRepositoryCustom {
    List<BoardResponseDto> findAllBoards();
}
