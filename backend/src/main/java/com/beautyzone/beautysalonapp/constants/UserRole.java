package com.beautyzone.beautysalonapp.constants;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_EMPLOYEE,
    ROLE_USER;

    @Override
    public String getAuthority() {
        return name();
    }
}