package com.shoppingmall.board.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoppingmall.board.domain.model.Board;
import com.shoppingmall.board.domain.repository.BoardRepository;
import com.shoppingmall.board.dto.request.BoardSaveRequestDto;
import com.shoppingmall.board.dto.response.BoardResponseDto;
import com.shoppingmall.board.service.BoardService;
import com.shoppingmall.user.dto.response.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    /**
     * DTO 를 전달받아 Repository 를 활용하여 데이터를 저장한다.
     * @param requestDto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public BoardResponseDto createBoard(BoardSaveRequestDto requestDto) throws Exception {
        // Security Context 로부터 요청한 유저의 정보를 가져온 후, DTO 에 유저 시퀀스를 삽입한다.
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        requestDto.setUserId(userDetails.getId());

        // Repository 를 활용하여 전달받은 DTO 를 엔티티로 변환한 후 저장한다.
        // Repository 의 save 메소드의 경우 반드시 엔티티여야만 저장이 가능하다.
        // save 메소드의 반환 값은 저장된 엔티티이다.
        Board boardEntity = boardRepository.save(requestDto.toEntity());

        // 엔티티를 바로 Response 로 전달하는 것은 영속성 컨텍스트 측면에서 바라볼 때 위험하다.
        // 따라서 Response DTO 의 형태로 변환하여 리턴한다.
        // 해당 경우에는 Response DTO 의 빌더 생성자 패턴을 통해 엔티티를 넘겨 처리한다.
        return BoardResponseDto.builder().entity(boardEntity).build();
    }

    /**
     * 전체 게시글을 조회한다.
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public List<BoardResponseDto> findBoards() throws Exception {
        List<Board> boardEntityList = boardRepository.findAllByStatus(1);

        return boardEntityList.stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
    }

}
