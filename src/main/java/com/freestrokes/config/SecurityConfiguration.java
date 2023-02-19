package com.freestrokes.config;

import com.freestrokes.auth.handler.CustomAccessDeniedHandler;
import com.freestrokes.auth.handler.CustomAuthenticationEntryPoint;
import com.freestrokes.auth.handler.OAuth2SuccessHandler;
import com.freestrokes.auth.security.JwtTokenFilter;
import com.freestrokes.auth.security.JwtTokenProvider;
import com.freestrokes.auth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// TODO: @EnableWebSecurity
// Spring Security 설정 활성화 어노테이션

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    private final JwtTokenProvider jwtTokenProvider;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    private final CustomOAuth2UserService customOAuth2UserService;

    // TODO: WebSecurityCustomizer
    // WebSecurity 설정

    // TODO: SecurityFilterChain
    // HttpSecurity 설정
    // 기존의 WebSecurityConfigurerAdapter 클래스가 Deprecated 되었기 때문에
    // SecurityFilterChain을 Bean으로 등록하여 사용.

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().antMatchers(
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/api/v1/login"
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .httpBasic().disable()  // spring security 로그인 페이지 사용 여부 (사용 안함)
            .csrf().disable()   // csrf 사용 여부 (사용안함)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세선 사용 여부 (사용 안함)
            .and()  // URL 인증여부 설정
                .authorizeRequests()
                .antMatchers(
                    "/swagger*/**",
                    "/api/v1/health-check",
                    "/api/v1/auth/**",
                    "/css/**",
                    "/exception/**",
                    "/favicon.ico",
                    "/login/oauth2/code/google",
                    "/user/oauth/password/**"
                )
                .permitAll()
                .anyRequest()   // 설정된 값들 이외의 나머지 URL
                .authenticated()    // 인증된 사용자
//            .and()
//                .logout()
//                .logoutSuccessUrl("/")
            .and()  // jwt 설정
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class) // jwt 필터 설정 (토큰 제공자와 사용할 클래스를 전달)
                .exceptionHandling()    // JwtAuthentication exception handling
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 토큰 인증 과정에서 발생하는 예외 처리를 위한 EndPoint
                .accessDeniedHandler(new CustomAccessDeniedHandler()) // 인가 실패시 예외를 발생시키는 Handler
            .and()  // OAuth2 설정
                .oauth2Login()
                .userInfoEndpoint() // oauth2 로그인 성공 후 가져올 설정
                .userService(customOAuth2UserService)   // oauth2 로그인 성공시 수행될 로직이 담긴 OAuth2UserService 구현체 등록
            .and()
//                .successHandler(oAuth2SuccessHandler)
            .and()
                .build();

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
