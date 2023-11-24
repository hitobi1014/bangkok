package com.ssafy.homes.domain.board.dao;


import com.ssafy.homes.domain.board.domain.Board;
import com.ssafy.homes.domain.board.domain.BoardType;

import java.util.List;

public interface BoardRepository{
    // 전체조회 상세조회 등록 수정 삭제

    List<Board> selectBoardAll();

    List<Board> selectBoardList(BoardType type);

    Board selectBoard(Long id);

    void insertBoard(Board board);

    Board updateBoard(Long id, Board board);

    void deleteBoard(Long id);

}

