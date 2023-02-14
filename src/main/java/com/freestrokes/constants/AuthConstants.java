package com.freestrokes.constants;

import lombok.Getter;

@Getter
public class AuthConstants {

    /*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	| Role
	|-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/

    public enum Role {
        ROLE_MANAGER,
        ROLE_USER,
        ROLE_GUEST
    }

}
