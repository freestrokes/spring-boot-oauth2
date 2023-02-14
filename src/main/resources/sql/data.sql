
-- board
INSERT INTO
    testdb.board(board_id, title, content, author)
VALUES
    ('c41feac0-3da0-4a42-8271-038ead97280d', 'title1', 'content1', 'author1'),
    ('ce0eca6b-0544-45ae-a2b9-7199126c21b5', 'title2', 'content2', 'author2'),
    ('8bfe97a1-c29f-4758-abe9-9c8302b74d23', 'title3', 'content3', 'author3')

-- board_comment
INSERT INTO
    testdb.board_comment(board_comment_id, board_id, content, author)
VALUES
    ('141feac0-3da0-4a42-8271-038ead97280d', 'c41feac0-3da0-4a42-8271-038ead97280d', 'board1 comment content1', 'author1'),
    ('241feac0-3da0-4a42-8271-038ead97280d', 'c41feac0-3da0-4a42-8271-038ead97280d', 'board1 comment content2', 'author2'),
    ('341feac0-3da0-4a42-8271-038ead97280d', 'c41feac0-3da0-4a42-8271-038ead97280d', 'board1 comment content3', 'author3'),
    ('4e0eca6b-0544-45ae-a2b9-7199126c21b5', 'ce0eca6b-0544-45ae-a2b9-7199126c21b5', 'board2 comment content1', 'author1'),
    ('5e0eca6b-0544-45ae-a2b9-7199126c21b5', 'ce0eca6b-0544-45ae-a2b9-7199126c21b5', 'board2 comment content2', 'author2'),
    ('6e0eca6b-0544-45ae-a2b9-7199126c21b5', 'ce0eca6b-0544-45ae-a2b9-7199126c21b5', 'board2 comment content3', 'author3'),
    ('7bfe97a1-c29f-4758-abe9-9c8302b74d23', '8bfe97a1-c29f-4758-abe9-9c8302b74d23', 'board3 comment content1', 'author1'),
    ('8bfe97a1-c29f-4758-abe9-9c8302b74d23', '8bfe97a1-c29f-4758-abe9-9c8302b74d23', 'board3 comment content2', 'author2'),
    ('9bfe97a1-c29f-4758-abe9-9c8302b74d23', '8bfe97a1-c29f-4758-abe9-9c8302b74d23', 'board3 comment content3', 'author3')
