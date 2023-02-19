package com.freestrokes.auth.service;

import com.freestrokes.auth.security.JwtTokenProvider;
import com.freestrokes.auth.domain.User;
import com.freestrokes.auth.dto.AuthDto;
import com.freestrokes.auth.repository.UserRepository;
import com.freestrokes.constants.AuthConstants;
import com.freestrokes.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final SecurityProperties securityProperties;

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

        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), findUser.getPassword())) {
            bodyObj.put("message", "Password do not match.");
            return new ResponseEntity<>(bodyObj, HttpStatus.EXPECTATION_FAILED);
        }

        // JWT 토큰 생성
        AuthDto.AuthTokenDto authToken = jwtTokenProvider.createJwtToken(findUser);

        bodyObj.put("accessToken", authToken.getAccessToken());
        bodyObj.put("refreshToken", authToken.getRefreshToken());
        bodyObj.put("accessTokenExpiration", authToken.getAccessTokenExpiration());
        bodyObj.put("refreshTokenExpiration", authToken.getRefreshTokenExpiration());

        // TODO: cookie 생성 확인
        // swagger-ui에서 /api/v1/auth/login api 테스트 후 확인 가능

        // Refresh Token 쿠키 생성
        Cookie cookie = new Cookie(AuthConstants.REFRESH_TOKEN, authToken.getRefreshToken());
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(securityProperties.getToken().getRefreshTokenExpiration());

        // 쿠키 등록
        httpServletResponse.addCookie(cookie);

        return new ResponseEntity<>(bodyObj, HttpStatus.OK);
    }

    // TODO: logout
//    public void logout() throws Exception {
//    }

}
