package com.freestrokes.controller;

import com.freestrokes.aop.LogExecutionTime;
import com.freestrokes.config.ApplicationProperties;
import com.freestrokes.constants.PathConstants;
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

    // TODO: ApplicationProperties 테스트를 위해 추가
    private final ApplicationProperties applicationProperties;

    private final BoardService boardService;

    // TODO: DI(Dependency Injection)에 대한 설명
    // 일반적으로 아래 코드처럼 생성자를 통해 의존성 주입을 설정해줌.
    // 스프링 IoC Container가 해당 의존성 타입에 맞는 bean을 만들어서 주입 (의존성 관리)
    // 의존성 주입에는 생성자, 필드 + setter 2가지 방법이 있는데
    // 레퍼런스에서는 생성자를 이용한 방법을 권장 (생성자는 lombok의 @RequiredArgsConstructor를 사용해서 생략 가능)
    // 필드 + setter를 사용한 경우엔 의존성 주입 없이 인스턴스 생성이 가능하다는 문제가 있음. (이 경우엔 @Autowired 어노테이션을 사용해야 함)
    // 생성자를 사용한 경우엔 순환 참조가 발생할 수 있음.

    // TODO: 생성자를 이용한 의존성 주입(DI) 예시.
//    public BoardController(ApplicationProperties applicationProperties, BoardService boardService) {
//        this.applicationProperties = applicationProperties;
//        this.boardService = boardService;
//    }

    /**
     * 게시글 목록 조회
     *
     * @return
     * @throws Exception
     */
    @GetMapping(path = PathConstants.BOARDS, produces = "application/json")
    @LogExecutionTime
    public ResponseEntity<List<BoardDto.ResponseDto>> getBoards() throws Exception {

        // TODO: ApplicationProperties 테스트를 위해 추가
        System.out.println("applicationProperties: " + applicationProperties);

        List<BoardDto.ResponseDto> result = boardService.getBoards();
        return new ResponseEntity<List<BoardDto.ResponseDto>>(result, HttpStatus.OK);
    }

    /**
     * 게시글 등록
     *
     * @param boardRequestDto
     * @return
     * @throws Exception
     */
    @PostMapping(path = PathConstants.BOARDS, produces = "application/json")
    public ResponseEntity<BoardDto.ResponseDto> postBoard(
        @RequestBody BoardDto.RequestDto boardRequestDto
    ) throws Exception {
        BoardDto.ResponseDto result = boardService.postBoard(boardRequestDto);
        return new ResponseEntity<BoardDto.ResponseDto>(result, HttpStatus.OK);
    }

    /**
     * 게시글 수정
     *
     * @param id
     * @param boardRequestDto
     * @return
     * @throws Exception
     */
    @PutMapping(path = PathConstants.BOARD, produces = "application/json")
    public ResponseEntity<BoardDto.ResponseDto> putBoard(
        @PathVariable String id,
        @RequestBody BoardDto.RequestDto boardRequestDto
    ) throws Exception {
        BoardDto.ResponseDto result = boardService.putBoard(id, boardRequestDto);
        return new ResponseEntity<BoardDto.ResponseDto>(result, HttpStatus.OK);
    }

    /**
     * 게시글 삭제
     *
     * @param id
     * @return
     * @throws Exception
     */
    @DeleteMapping(path = PathConstants.BOARD, produces = "application/json")
    public ResponseEntity<?> deleteBoard(
        @PathVariable String id
    ) throws Exception {
        boardService.deleteBoard(id);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

}
