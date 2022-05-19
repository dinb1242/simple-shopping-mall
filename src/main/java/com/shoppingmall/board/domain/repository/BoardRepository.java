package com.shoppingmall.board.domain.repository;

import com.shoppingmall.board.domain.model.Board;
import com.shoppingmall.board.domain.repository.custom.BoardRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
    List<Board> findAllByStatus(Integer status);
}
