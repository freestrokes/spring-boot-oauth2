package com.freestrokes.controller;

import com.freestrokes.aop.LogExecutionTime;
import com.freestrokes.constants.PathConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO: @RequestMapping
// 해당 컨트롤러 하위 전체 메서드에 공통 path 설정이 필요한 경우 class 상단에 어노테이션 사용.
// @RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class HealthController {

    /**
     * 게시글 목록 조회
     *
     * @return
     * @throws Exception
     */
    @GetMapping(path = PathConstants.HEALTH_CHECK, produces = "application/json")
    @LogExecutionTime
    public ResponseEntity<?> getBoards() throws Exception {
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

}
