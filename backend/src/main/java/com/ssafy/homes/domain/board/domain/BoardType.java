package com.ssafy.homes.domain.board.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public enum BoardType {
    //공지사항, 뉴스, 자유게시판
    NOTICE("NOTICE"), NEWS("NEWS"), FREE("FREE");
    private final String value;

    BoardType(String value) {
        this.value = value;
    }

    public static BoardType of(String type){
        for (BoardType bt : BoardType.values()){
            if (bt.value.equals(type)){
                return bt;
            }
        }
        throw new IllegalStateException("일치하는 타입이 없습니다.");
    }

}
