package com.shoppingmall.board.service;

import com.shoppingmall.board.dto.request.BoardSaveRequestDto;
import com.shoppingmall.board.dto.response.BoardResponseDto;

public interface BoardService {
    BoardResponseDto createBoard(BoardSaveRequestDto requestDto) throws Exception;
}
