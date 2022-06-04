package com.shoppingmall.board.domain.repository.custom;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoppingmall.board.domain.model.QBoard;
import com.shoppingmall.board.dto.response.BoardResponseDto;
import com.shoppingmall.user.domain.model.QUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private QUser user = QUser.user;
    private QBoard board = QBoard.board;

    @Override
    public List<BoardResponseDto> findAllBoards() {
        List<BoardResponseDto> results = queryFactory.select(
                Projections.fields(
                        BoardResponseDto.class,
                        board.id.as("id"),
                        board.userId.as("userId"),
                        user.username.as("username"),
                        board.type.as("type"),
                        board.content.as("content"),
                        board.createdAt.as("createdAt"),
                        board.updatedAt.as("updatedAt"),
                        board.status.as("status")
                )
        ).from(board)
                .innerJoin(user).on(user.id.eq(board.userId))
                .where(board.status.eq(1))
                .orderBy(board.createdAt.desc())
                .fetch();
        return results;
    }
}
