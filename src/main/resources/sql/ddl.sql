
DROP TABLE IF EXISTS board;

CREATE TABLE board (
    id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    title varchar(500) DEFAULT NULL COMMENT '제목',
    content text DEFAULT NULL COMMENT '내용',
    author varchar(100) DEFAULT NULL COMMENT '작성자',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='게시판';
