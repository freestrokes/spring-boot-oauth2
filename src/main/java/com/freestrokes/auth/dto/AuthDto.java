package com.freestrokes.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class AuthDto {

    @Getter
    public static class RequestDto {
        private String email;
        private String password;

        @Builder(toBuilder = true)
        public RequestDto(
            String email,
            String password
        ) {
            this.email = email;
            this.password = password;
        }
    }

    @Getter
    public static class AuthTokenDto {
        private String accessToken;
        @JsonIgnore
        private String refreshToken;
        @JsonIgnore
        private Date accessTokenExpiration;
        @JsonIgnore
        private Date refreshTokenExpiration;

        @Builder(toBuilder = true)
        public AuthTokenDto(
            String accessToken,
            String refreshToken,
            Date accessTokenExpiration,
            Date refreshTokenExpiration
        ) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
            this.accessTokenExpiration = accessTokenExpiration;
            this.refreshTokenExpiration = refreshTokenExpiration;
        }
    }

}
