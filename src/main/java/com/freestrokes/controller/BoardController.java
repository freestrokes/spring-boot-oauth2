package com.freestrokes.controller;

import com.freestrokes.domain.Board;
import com.freestrokes.dto.BoardDto;
import com.freestrokes.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @GetMapping(path = "/api/v1/boards", produces = "application/json")
    public ResponseEntity<List<BoardDto>> getBoards() throws Exception {
        List<BoardDto> result = boardService.getBoards();
        return new ResponseEntity<List<BoardDto>>(result, HttpStatus.OK);
    }

    @PostMapping(path = "/api/v1/boards", produces = "application/json")
    public ResponseEntity<Board> postBoard(@RequestBody BoardDto boardDto) throws Exception {
        Board result = boardService.postBoard(boardDto);
        return new ResponseEntity<Board>(result, HttpStatus.OK);
    }

    @PutMapping(path = "/api/v1/boards/{id}", produces = "application/json")
    public ResponseEntity<Board> putBoard(@PathVariable Long id, @RequestBody BoardDto boardDto) throws Exception {
        Board result = boardService.putBoard(id, boardDto);
        return new ResponseEntity<Board>(result, HttpStatus.OK);
    }

    @DeleteMapping(path = "/api/v1/boards/{id}", produces = "application/json")
    public ResponseEntity<?> deleteBoard(@PathVariable Long id) throws Exception {
        boardService.deleteBoard(id);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

}
