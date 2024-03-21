package airbnb.entities.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    VENDOR,
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
