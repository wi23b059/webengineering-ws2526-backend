// src/main/java/at/technikum/springrestbackend/security/UserPrincipal.java
package at.technikum.springrestbackend.security;

import at.technikum.springrestbackend.entity.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Security representation of an authenticated user.
 * <p>
 * Implements Spring Security's UserDetails interface and provides
 * the user's ID, username, password and role for authentication and
 * authorization purposes.
 */
@Getter
public class UserPrincipal implements UserDetails {
    private final UUID id;
    private final String username;
    private final String password;
    private final Role role;

    public UserPrincipal(UUID id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Returns the authorities granted to the user.
     * Converts the user's Role enum into Spring's "ROLE_*" format.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() { return password; }

    @Override
    public String getUsername() { return username; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
