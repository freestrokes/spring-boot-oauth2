package com.freestrokes.auth.controller;

import com.freestrokes.aop.LogExecutionTime;
import com.freestrokes.constants.PathConstants;
import com.freestrokes.auth.dto.AuthDto;
import com.freestrokes.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    /**
     * 로그인
     *
     * @return
     * @throws Exception
     */
    @PostMapping(path = PathConstants.LOGIN, produces = "application/json")
    @LogExecutionTime
    public ResponseEntity<?> login(
        @RequestBody AuthDto.RequestDto loginRequestDto
    ) throws Exception {
        return authService.login(loginRequestDto);
//        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

}
