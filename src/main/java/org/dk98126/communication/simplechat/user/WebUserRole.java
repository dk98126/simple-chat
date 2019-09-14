package org.dk98126.communication.simplechat.user;

import org.springframework.security.core.GrantedAuthority;

public enum WebUserRole implements GrantedAuthority {
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
