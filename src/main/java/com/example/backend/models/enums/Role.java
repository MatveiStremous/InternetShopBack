package com.example.backend.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER_ROLE, MODERATOR_ROLE, ADMIN_ROLE;

    @Override
    public String getAuthority() {
        return name();
    }
}
