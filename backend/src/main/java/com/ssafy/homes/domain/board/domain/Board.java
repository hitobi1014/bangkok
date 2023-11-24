package com.ssafy.homes.domain.board.domain;

import com.ssafy.homes.domain.member.domain.Member;
import com.ssafy.homes.global.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "boardId")
    private Long id;

    @Enumerated(EnumType.STRING)
    private BoardType type;
    @Column(columnDefinition = "TEXT")
    private String title;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;
    private int hit;

//    @OneToMany(mappedBy ="board", cascade = CascadeType.MERGE, orphanRemoval = true)
//    @OneToMany(fetch = FetchType.EAGER)
    @OneToMany(fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

//    @ManyToOne(fetch = FetchType.EAGER)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    // 임시 회원정보를 넣기 위한 setter
    public void setMember(Member member) {
        this.member = member;
    }

    public void updateBoard(Board board) {
        this.type = board.getType();
        this.title = board.getTitle();
        this.content = board.getContent();
    }

    @Builder
    private Board(BoardType type, String title, String content) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.hit = 0; // 최초 조회수는 0
    }

}
