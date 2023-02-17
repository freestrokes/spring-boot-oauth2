package com.freestrokes.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class OAuth2UserDto {

    @Getter
    public static class RequestDto {
        private String userNameAttributeName;
        private String name;
        private String email;
        private String picture;
        private Map<String, Object> attributes;

        @Builder(toBuilder = true)
        public RequestDto(
            String userNameAttributeName,
            String name,
            String email,
            String picture,
            Map<String, Object> attributes
        ) {
            this.userNameAttributeName = userNameAttributeName;
            this.name = name;
            this.email = email;
            this.picture = picture;
            this.attributes = attributes;
        }
    }

    @Getter
    public static class ResponseDto {
        private String userId;
        private String name;
        private String email;
        private String picture;
        private Map<String, Object> attributes;

        @Builder(toBuilder = true)
        public ResponseDto(
            String userId,
            String name,
            String email,
            String picture,
            Map<String, Object> attributes
        ) {
            this.userId = userId;
            this.name = name;
            this.email = email;
            this.picture = picture;
            this.attributes = attributes;
        }
    }

}
