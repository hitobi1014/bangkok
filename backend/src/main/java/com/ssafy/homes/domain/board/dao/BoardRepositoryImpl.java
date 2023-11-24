package com.ssafy.homes.domain.board.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.homes.domain.board.domain.Board;
import com.ssafy.homes.domain.board.domain.BoardType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ssafy.homes.domain.board.domain.QBoard.board;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BoardRepositoryImpl implements BoardRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    @Override
    public List<Board> selectBoardAll() {
        return jpaQueryFactory.selectFrom(board)
                .fetch();
    }

    @Override
    public List<Board> selectBoardList(BoardType type) {
        return jpaQueryFactory.selectFrom(board)
                .where(board.type.eq(type))
                .fetch();
    }

    @Override
    public Board selectBoard(Long id) {
        return jpaQueryFactory.selectFrom(board)
                .where(board.id.eq(id))
                .fetchOne();
    }

    @Override
    public void insertBoard(Board board) {
        log.info("레포지토리 보드 : {}", board);
        em.persist(board);
    }

    @Override
    public Board updateBoard(Long id, Board board) {
        Board getBoard = em.find(Board.class, id);
        getBoard.updateBoard(board);
        return getBoard;
    }

    @Override
    public void deleteBoard(Long id) {
        Board getBoard = em.find(Board.class, id);
        em.remove(getBoard);
    }
}
