package com.sample.util;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ADMIN,
    USER;

    private static final String PREFIX = "ROLE_";

    public static Role fromAuthority(String authority) {
        return Role.valueOf(authority.substring(PREFIX.length()));
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
