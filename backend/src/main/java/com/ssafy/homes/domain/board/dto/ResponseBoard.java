package com.ssafy.homes.domain.board.dto;

import com.ssafy.homes.domain.board.domain.Board;
import com.ssafy.homes.domain.board.domain.BoardType;
import com.ssafy.homes.domain.board.domain.Comment;
import com.ssafy.homes.domain.member.domain.Member;
import com.ssafy.homes.domain.member.dto.ResponseMember;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseBoard {

    private Long id;
    private BoardType type;
    private String title;
    private String content;
    private int hit;
//    private Member member;
    private Long memberId;
    private String memberName;

    private List<Comment> comments = new ArrayList<>();

    public ResponseBoard toDto(Board board) {
        id = board.getId();
        type = board.getType();
        title = board.getTitle();
        content = board.getContent();
        hit = board.getHit();
        memberId = board.getMember().getId();
        memberName = board.getMember().getName();

        comments = board.getComments();
        return this;
    }
}
