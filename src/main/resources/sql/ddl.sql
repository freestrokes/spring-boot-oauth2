
DROP TABLE IF EXISTS board;
DROP TABLE IF EXISTS board_comment;

CREATE TABLE board (
    board_id varchar(100) NOT NULL COMMENT '게시글 id',
    title varchar(500) DEFAULT NULL COMMENT '제목',
    content text DEFAULT NULL COMMENT '내용',
    author varchar(100) DEFAULT NULL COMMENT '작성자',
    PRIMARY KEY (board_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='게시글';

CREATE TABLE board_comment (
    board_comment_id varchar(100) NOT NULL COMMENT '게시글 댓글 id',
    board_id varchar(100) NOT NULL COMMENT '게시글 id',
    content text DEFAULT NULL COMMENT '내용',
    author varchar(100) DEFAULT NULL COMMENT '작성자',
    PRIMARY KEY (board_comment_id),
    CONSTRAINT board_comment_fk1 FOREIGN KEY (board_id) REFERENCES board (board_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='게시글 댓글';
