package com.ssafy.homes.domain.board.api;

import com.ssafy.homes.domain.board.application.BoardService;
import com.ssafy.homes.domain.board.domain.BoardType;
import com.ssafy.homes.domain.board.dto.ModifyBoard;
import com.ssafy.homes.domain.board.dto.RegistBoard;
import com.ssafy.homes.domain.board.dto.ResponseBoard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("")
    public ResponseEntity<?> getBoardAll(){
        return ResponseEntity.ok(boardService.getBoardAll());
    }

    /**
     * 게시판 타입을 입력받아
     * 게시글 전체를 조회한다.
     */
    @GetMapping("/{type}")
    public ResponseEntity<?> getBoards(@PathVariable BoardType type) {

        List<ResponseBoard> boardList = boardService.getBoardList(type);
        return ResponseEntity.ok(boardList);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getBoard(@PathVariable Long id){
        ResponseBoard board = boardService.getBoard(id);
        return ResponseEntity.ok(board);
    }

    @PostMapping("/regist")
    public ResponseEntity<?> registBoard(@RequestBody RegistBoard registBoard){
        boardService.registBoard(registBoard);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/modify/{id}")
    public ResponseEntity<?> modifyBoard(@PathVariable Long id, @RequestBody ModifyBoard modifyBoard){
        ResponseBoard responseBoard = boardService.modifyBoard(id, modifyBoard);
        return ResponseEntity.ok(responseBoard);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeBoard(@PathVariable Long id) {
        boardService.removeBoard(id);
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
