package com.ssafy.homes.domain.board.application;

import com.ssafy.homes.domain.board.dao.BoardRepository;
import com.ssafy.homes.domain.board.domain.Board;
import com.ssafy.homes.domain.board.domain.BoardType;
import com.ssafy.homes.domain.board.dto.ModifyBoard;
import com.ssafy.homes.domain.board.dto.RegistBoard;
import com.ssafy.homes.domain.board.dto.ResponseBoard;
import com.ssafy.homes.domain.member.dao.MemberRepository;
import com.ssafy.homes.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;

    //임시 회원정보가져오는용도
    private final MemberRepository memberRepository;

    @Override
    public List<ResponseBoard> getBoardAll() {
        return boardRepository.selectBoardAll()
                .stream()
                .map(b -> new ResponseBoard().toDto(b))
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponseBoard> getBoardList(BoardType type) {
        return boardRepository.selectBoardList(type)
                .stream()
                .map(b -> new ResponseBoard().toDto(b))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseBoard getBoard(Long id) {
        Board getBoard = boardRepository.selectBoard(id);
        return new ResponseBoard().toDto(getBoard);
    }

    @Override
    @Transactional
    public void registBoard(RegistBoard board) {
        // 회원정보 가져오는 공통로직 만들기
        // 임시 회원정보
        Long id = 1L;
        Member member = memberRepository.selectMemberById(id);

        Board entity = board.toEntity(board);
        entity.setMember(member);
        log.info("회원 값 : {}", member);
        log.info("보드 값 : {}", entity);
        boardRepository.insertBoard(entity);
    }

    @Override
    public ResponseBoard modifyBoard(Long id, ModifyBoard board) {
        Board result = boardRepository.updateBoard(id, board.toEntity(board));
        return new ResponseBoard().toDto(result);
    }

    @Override
    @Transactional
    public void removeBoard(Long id) {
        boardRepository.deleteBoard(id);
    }
}
