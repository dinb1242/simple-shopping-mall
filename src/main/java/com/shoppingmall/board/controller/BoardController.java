package com.shoppingmall.board.controller;

import com.shoppingmall.board.dto.request.BoardSaveRequestDto;
import com.shoppingmall.board.dto.response.BoardResponseDto;
import com.shoppingmall.board.service.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"[App] 게시글 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("")
    @ApiOperation(value = "[App] 게시글 작성", notes = "클라이언트가 DTO 를 전달받아 게시글을 작성한다.")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(code = 201, message = "생성 성공", response = BoardResponseDto.class)
    })
    public ResponseEntity<BoardResponseDto> createBoard(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @RequestBody BoardSaveRequestDto requestDto
            ) throws Exception {
        return new ResponseEntity<>(boardService.createBoard(requestDto), HttpStatus.CREATED);
    }

}
