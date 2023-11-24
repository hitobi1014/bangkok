package com.ssafy.homes.global.common;

import com.ssafy.homes.domain.board.domain.BoardType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BoardTypeConverter implements Converter<String, BoardType> {


    @Override
    public BoardType convert(String type) {
        return BoardType.of(type);
    }
}
