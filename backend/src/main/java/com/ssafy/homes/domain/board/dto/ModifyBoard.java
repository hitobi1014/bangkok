package com.ssafy.homes.domain.board.dto;

import com.ssafy.homes.domain.board.domain.Board;
import com.ssafy.homes.domain.board.domain.BoardType;
import lombok.Data;

@Data
public class ModifyBoard {
    private Long id;
    private BoardType type;
    private String title;
    private String content;

    public Board toEntity(ModifyBoard modifyBoard) {
        return Board.builder()
                .type(modifyBoard.getType())
                .title(modifyBoard.getTitle())
                .content(modifyBoard.getContent())
                .build();
    }
}

