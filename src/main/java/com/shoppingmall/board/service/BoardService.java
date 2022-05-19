package com.shoppingmall.board.service;

import com.shoppingmall.board.dto.request.BoardSaveRequestDto;
import com.shoppingmall.board.dto.response.BoardResponseDto;

import java.util.List;

public interface BoardService {
    BoardResponseDto createBoard(BoardSaveRequestDto requestDto) throws Exception;
    List<BoardResponseDto> findBoards() throws Exception;
}
