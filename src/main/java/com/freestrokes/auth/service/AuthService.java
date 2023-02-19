package com.freestrokes.auth.service;

import com.freestrokes.auth.security.JwtTokenProvider;
import com.freestrokes.auth.domain.User;
import com.freestrokes.auth.dto.AuthDto;
import com.freestrokes.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;

    /**
     * 로그인
     *
     * @param loginRequestDto
     * @return
     * @throws Exception
     */
    @Transactional
    public ResponseEntity<?> login(
        AuthDto.RequestDto loginRequestDto,
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse
    ) throws Exception {

        // 사용자 조회
        User findUser = userRepository.findByEmail(loginRequestDto.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("User [" + loginRequestDto.getEmail() + "] not found."));

        JSONObject bodyObj = new JSONObject();

        // 비밀번호 확인
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), findUser.getPassword())) {
            bodyObj.put("message", "Password do not match.");
            return new ResponseEntity<>(bodyObj, HttpStatus.EXPECTATION_FAILED);
        }

        // JWT 토큰 생성
        AuthDto.AuthTokenDto authToken = jwtTokenProvider.createJwtToken(findUser, httpServletRequest, httpServletResponse);

        bodyObj.put("accessToken", authToken.getAccessToken());
        bodyObj.put("refreshToken", authToken.getRefreshToken());
        bodyObj.put("accessTokenExpiration", authToken.getAccessTokenExpiration());
        bodyObj.put("refreshTokenExpiration", authToken.getRefreshTokenExpiration());

        return new ResponseEntity<>(bodyObj, HttpStatus.OK);
    }

}
