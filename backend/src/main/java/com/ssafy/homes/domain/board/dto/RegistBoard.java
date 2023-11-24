package com.ssafy.homes.domain.board.dto;

import com.ssafy.homes.domain.board.domain.Board;
import com.ssafy.homes.domain.board.domain.BoardType;
import lombok.Data;

@Data
public class RegistBoard {

    private BoardType type;
    private String title;
    private String content;

    public Board toEntity(RegistBoard registBoard) {
        return Board.builder()
                .type(registBoard.getType())
                .title(registBoard.getTitle())
                .content(registBoard.getContent())
                .build();
    }

}
