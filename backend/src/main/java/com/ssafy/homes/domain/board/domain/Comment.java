package com.ssafy.homes.domain.board.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    @GeneratedValue
    @Column(name = "commentId")
    private Long id;

    // 양방향 관계 필요하면 주석해제
//    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Board.class)
//    @JoinColumn(name = "boardId", updatable = false)
//    private Board board;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;
}
