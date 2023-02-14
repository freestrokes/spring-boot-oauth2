package com.freestrokes.dto;

import com.freestrokes.domain.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardCommentDto {

    @Getter
    public static class RequestDto {
        private String boardId;
        private String content;
        private String author;

        @Builder(toBuilder = true)
        public RequestDto(
            String boardId,
            String content,
            String author
        ) {
            this.boardId = boardId;
            this.content = content;
            this.author = author;
        }
    }

    @Getter
    public static class ResponseDto {
        private String boardCommentId;
        private Board board;
        private String content;
        private String author;

        @Builder(toBuilder = true)
        public ResponseDto(
            String boardCommentId,
            Board board,
            String content,
            String author
        ) {
            this.boardCommentId = boardCommentId;
            this.board = board;
            this.content = content;
            this.author = author;
        }
    }

}
