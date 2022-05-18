package com.shoppingmall.board.domain.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoppingmall.board.domain.model.QBoard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private QBoard board = QBoard.board;

}
