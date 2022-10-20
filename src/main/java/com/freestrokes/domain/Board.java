package com.freestrokes.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(length = 100)
    private String author;

    public void update(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.author = board.getAuthor();
    }

    @Builder
    public Board(
        String title,
        String content,
        String author
    ) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

}
