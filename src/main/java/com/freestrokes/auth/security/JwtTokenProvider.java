package com.freestrokes.auth.security;

import com.freestrokes.auth.domain.User;
import com.freestrokes.properties.SecurityProperties;
import com.freestrokes.constants.AuthConstants;
import com.freestrokes.auth.dto.AuthDto;
import com.freestrokes.auth.service.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    private final SecurityProperties securityProperties;

    private final CustomUserDetailsService customUserDetailsService;

    /**
     * JWT 토큰 생성
     *
     * @param user
     * @return
     */
    public AuthDto.AuthTokenDto createJwtToken(
        User user,
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse
    ) {

        Date now = new Date();

        // Access Token 만료 시간
        Date accessTokenExpiration = new Date(now.getTime() + securityProperties.getToken().getAccessTokenExpiration());

        // Refresh Token 만료 시간
        Date refreshTokenExpiration = new Date(now.getTime() + securityProperties.getToken().getRefreshTokenExpiration());

        // claims
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("name", user.getName());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());

        // secret key
        Key secretKey = Keys.hmacShaKeyFor(securityProperties.getToken().getSecretKey().getBytes());

        // Access Token 생성
        String accessToken = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(accessTokenExpiration)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            // TODO: Deprecated
//            .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(securityProperties.getToken().getSecretKey().getBytes()))
            .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(refreshTokenExpiration)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();

        // Refresh Token 쿠키에 추가
        Cookie cookie = new Cookie(AuthConstants.REFRESH_TOKEN, refreshToken);
            cookie.setPath("/");
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(securityProperties.getToken().getRefreshTokenExpiration());

        httpServletResponse.addCookie(cookie);

        return AuthDto.AuthTokenDto.builder()
            .accessToken(accessToken)
            .accessTokenExpiration(accessTokenExpiration)
            .refreshToken(refreshToken)
            .refreshTokenExpiration(refreshTokenExpiration)
            .build();

    }

    /**
     * JWT 토큰 인증 조회
     *
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token) {
        // 토큰의 username(email)으로 사용자 조회
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(getUsername(token));

        // Authentication 반환
        return new UsernamePasswordAuthenticationToken(
            userDetails,
            "",
            userDetails.getAuthorities()
        );
    }

    /**
     * JWT 토큰 username(email) 조회
     *
     * ex) jwtTokenProvider.getUserEmail(accessToken);
     *
     * @param token
     * @return
     */
    public String getUsername(String token) {
        // TODO: Jwts.parser() Deprecated
//        Jwts.parser()
//            .setSigningKey(securityProperties.getToken().getSecretKey().getBytes())
//            .parseClaimsJws(token).getBody().getSubject()

        // 토큰 subject에 설정된 username(email) 조회 후 반환
        return Jwts.parserBuilder()
            .setSigningKey(securityProperties.getToken().getSecretKey().getBytes())
            .build()
            .parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * JWT 토큰 가져오기 (요청 헤더)
     *
     * @param httpServletRequest
     * @return
     */
    public String resolveToken(HttpServletRequest httpServletRequest) {
        // request header에서 jwt 토큰 가져오기
        String bearerToken = httpServletRequest.getHeader(AuthConstants.AUTH_HEADER);

        if (bearerToken != null && bearerToken.startsWith(AuthConstants.AUTH_TYPE)) {
            // 인증 유형(bearer)을 제외한 토큰 값을 반환
            return bearerToken.substring(AuthConstants.AUTH_TYPE.length()).trim();
        }

        return null;
    }

    /**
     * JWT 토큰 만료 확인 (만료 여부 true/false 반환)
     *
     * @param tokenㅠ
     * @return
     */
    public boolean validateToken(String token) {
        try {
            Date now = new Date();

            // claims 조회
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(securityProperties.getToken().getSecretKey().getBytes())
                .build()
                .parseClaimsJws(token).getBody();

            return !claims.getExpiration().before(now);
        } catch (Exception e) {
            return false;
        }
    }

}
