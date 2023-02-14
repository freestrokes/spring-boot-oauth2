package com.freestrokes.controller;

import com.freestrokes.constants.PathConstants;
import com.freestrokes.dto.BoardCommentDto;
import com.freestrokes.service.BoardCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class BoardCommentController {

    private final BoardCommentService boardCommentService;

    /**
     * 게시글 댓글 등록
     *
     * @param boardCommentRequestDto
     * @return
     * @throws Exception
     */
    @PostMapping(path = PathConstants.BOARD_COMMENTS, produces = "application/json")
    public ResponseEntity<BoardCommentDto.ResponseDto> postBoardComment(
        @RequestBody BoardCommentDto.RequestDto boardCommentRequestDto
    ) throws Exception {
        BoardCommentDto.ResponseDto result = boardCommentService.postBoardComment(boardCommentRequestDto);
        return new ResponseEntity<BoardCommentDto.ResponseDto>(result, HttpStatus.OK);
    }

    /**
     * 게시글 댓글 수정
     *
     * @param id
     * @param boardCommentRequestDto
     * @return
     * @throws Exception
     */
    @PutMapping(path = PathConstants.BOARD_COMMENT, produces = "application/json")
    public ResponseEntity<BoardCommentDto.ResponseDto> putBoardComment(
        @PathVariable String id,
        @RequestBody BoardCommentDto.RequestDto boardCommentRequestDto
    ) throws Exception {
        BoardCommentDto.ResponseDto result = boardCommentService.putBoardComment(id, boardCommentRequestDto);
        return new ResponseEntity<BoardCommentDto.ResponseDto>(result, HttpStatus.OK);
    }

    /**
     * 게시글 댓글 삭제
     *
     * @param id
     * @return
     * @throws Exception
     */
    @DeleteMapping(path = PathConstants.BOARD_COMMENT, produces = "application/json")
    public ResponseEntity<?> deleteBoardComment(
        @PathVariable String id
    ) throws Exception {
        boardCommentService.deleteBoardComment(id);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

}
