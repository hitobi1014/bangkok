package com.ssafy.homes.domain.board.application;

import com.ssafy.homes.domain.board.domain.BoardType;
import com.ssafy.homes.domain.board.dto.ModifyBoard;
import com.ssafy.homes.domain.board.dto.RegistBoard;
import com.ssafy.homes.domain.board.dto.ResponseBoard;

import java.util.List;

public interface BoardService {

    List<ResponseBoard> getBoardAll();
    List<ResponseBoard> getBoardList(BoardType type);

    ResponseBoard getBoard(Long id);

    void registBoard (RegistBoard board);

    ResponseBoard modifyBoard(Long id, ModifyBoard board);

    void removeBoard(Long id);
}
