package com.freestrokes.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Data
@Component
@ConfigurationProperties(
    prefix = "spring.security.jwt",
    ignoreUnknownFields = false
)
public class SecurityProperties {

    private Token token;

    @Getter
    @Setter
    public static class Token {
        private String secretKey;
        private int accessTokenExpiration;
        private int refreshTokenExpiration;
    }

}

