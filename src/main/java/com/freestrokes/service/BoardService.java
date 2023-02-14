package com.freestrokes.service;

import com.freestrokes.domain.Board;
import com.freestrokes.domain.BoardComment;
import com.freestrokes.dto.BoardDto;
import com.freestrokes.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    // TODO: proxy 객체
    // 연관관계 매핑된 객체 조회시 hibernate interceptor에 의해 proxy 객체가 생성되는 경우가 있음
    // proxy 객체의 필드 중 하나를 get 해오면 영속 상태의 객체로 매핑됨.
    // 이 부분 찾아서 확인해보기.

    // TODO: @Transactional(readOnly = true)
    // 서비스 계층에서 트랙잭션을 시작하면 repository 계층에서도 해당 트랜잭션을 전파 받아서 사용.
    // 지연 로딩 시점까지 세션을 유지하여 LazyInitializationException 해결 가능.
    // 아래와 같이 세션 유지가 필요한 메서드에 @Transactional(readOnly = true) 붙여줌.
    // but, RDB에도 연관관계가 설정되어 있지 않은 경우엔 LazyInitializationException 계속 발생할 수 있음
    // 이러한 경우엔 @ManyToOne 설정해준 쪽에서 @EntityGraph 사용하여 해결.

    /**
     * 게시글 목록 조회
     *
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public List<BoardDto.ResponseDto> getBoards() throws Exception {
        // TODO: CASE1) 1:N 양방향 매핑 조회 후 DTO 변환
        // 게시글 조회
        List<BoardDto.ResponseDto> boardsResponseDto = boardRepository.findAll()
            .stream()
            .map(board -> {
                return BoardDto.ResponseDto.builder()
                    .boardId(board.getBoardId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .author(board.getAuthor())
                    .boardComments(
                        board.getBoardComments().stream().map(boardComment -> {
                            return BoardComment.builder()
                                .boardCommentId(boardComment.getBoardCommentId())
                                .board(board)
                                .content(boardComment.getContent())
                                .author(boardComment.getAuthor())
                                .build();
                        }).collect(Collectors.toList())
                    )
                    .build();
            })
            .collect(Collectors.toList());

        //TODO: CASE2) 1:N 양방향 매핑 조회 후 DTO 변환
//        List<Board> boardList = boardRepository.findAll();
//        List<BoardDto.ResponseDto> boardsResponseDto = new ArrayList<>();
//
//        for (Board board : boardList) {
//            List<BoardComment> boardComments = new ArrayList<>();
//
//            // Board Comment DTO
//            if (board.getBoardComments().size() > 0) {
//                board.getBoardComments().stream().forEach(boardComment -> {
//                    boardComments.add(
//                        BoardComment.builder()
//                            .boardCommentId(boardComment.getBoardCommentId())
//                            .board(board)
//                            .content(boardComment.getContent())
//                            .author(boardComment.getAuthor())
//                            .build()
//                    );
//                });
//            }
//
//            // Board DTO
//            boardsResponseDto.add(
//                BoardDto.ResponseDto.builder()
//                    .boardId(board.getBoardId())
//                    .title(board.getTitle())
//                    .content(board.getContent())
//                    .author(board.getAuthor())
//                    .boardComments(boardComments)
//                    .build()
//            );
//        }

        return boardsResponseDto;

    }

    /**
     * 게시글 등록
     *
     * @param boardRequestDto
     * @return
     * @throws Exception
     */
    @Transactional
    public BoardDto.ResponseDto postBoard(BoardDto.RequestDto boardRequestDto) throws Exception {

        // TODO: Optional을 이용한 중복 체크가 필요한 경우
//        Optional<Board> existBoard = boardRepository.findByTitle(boardRequestDto.getTitle());
//        existBoard.ifPresent(item -> {
//            try {
//                throw new Exception();
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });

        // 게시글 생성
        Board board = Board.builder()
            .title(boardRequestDto.getTitle())
            .content(boardRequestDto.getContent())
            .author(boardRequestDto.getAuthor())
            .build();

        // 게시글 저장
        boardRepository.save(board);

        return BoardDto.ResponseDto.builder()
            .boardId(board.getBoardId())
            .title(board.getTitle())
            .content(board.getContent())
            .author(board.getAuthor())
            .build();

    }

    /**
     * 게시글 수정
     *
     * @param id
     * @param boardRequestDto
     * @return
     * @throws Exception
     */
    @Transactional
    public BoardDto.ResponseDto putBoard(String id, BoardDto.RequestDto boardRequestDto) throws Exception {

        // 게시글 조회
        Board findBoard = boardRepository.findById(id).orElseThrow(NoSuchElementException::new);

        // TODO: @Transactional 어노테이션 사용하여 update 하려는 경우
        // @Transactional 어노테이션을 명시하여 repository save() 호출 없이 저장 가능.
        // board builder() 생성 없이 findBoard > updateBoard() 호출하는 것 만으로도 저장 가능
        findBoard.updateBoard(
            boardRequestDto.getTitle(),
            boardRequestDto.getContent(),
            boardRequestDto.getAuthor()
        );

        // TODO: @Transactional 어노테이션이 없이 update 하려는 경우
//        Board board = Board.builder()
//            .title(boardRequestDto.getTitle())
//            .content(boardRequestDto.getContent())
//            .author(boardRequestDto.getAuthor())
//            .build();
//
//        findBoard.updateBoard(board);
//
//        boardRepository.save(findBoard);

        return BoardDto.ResponseDto.builder()
            .boardId(findBoard.getBoardId())
            .title(findBoard.getTitle())
            .content(findBoard.getContent())
            .author(findBoard.getAuthor())
            .build();

    }

    /**
     * 게시글 삭제
     *
     * @param boardId
     * @throws Exception
     */
    @Transactional
    public void deleteBoard(String boardId) throws Exception {
        // 게시글 삭제
        boardRepository.deleteById(boardId);
    }

}
