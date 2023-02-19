package com.freestrokes.auth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// TODO: JwtTokenFilter
// 로그인시 request에 담긴 토큰의 유효성을 확인하는 필터

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilterInternal(
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse,
        FilterChain filterChain
    ) throws IOException, ServletException {

        // request header에서 jwt 토큰 가져오기
        String token = jwtTokenProvider.resolveToken(httpServletRequest);

        //  토큰 유효성 검사
        if (token != null && jwtTokenProvider.validateToken(token)) {

            // 토큰을 인증하고 결과를 인증객체(Authentication)에 저장
            Authentication authentication = jwtTokenProvider.getAuthentication(token);

            // 토큰이 유효한 경우 인증된 상태를 유지하도록 SecurityContext에 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // UsernamePasswordAuthenticationFilter 호출
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
