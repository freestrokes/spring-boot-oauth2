package com.freestrokes.constants;

import lombok.Getter;

@Getter
public class AuthConstants {

    public static final String AUTH_HEADER = "Authorization";
    public static final String AUTH_TYPE = "Bearer";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";

    // TODO: 'ROLE_' prefix
    // spring security가 .hasRole() 권한 체크시 'ROLE_' prefix를 붙여줌
    // 따라서 'ROLE_' prefix 생략하여 enum 작성 필요.
    // java.lang.IllegalArgumentException: role should not start with 'ROLE_' since it is automatically inserted.

    public enum Role {
        MANAGER,
        USER,
        GUEST
    }

}
