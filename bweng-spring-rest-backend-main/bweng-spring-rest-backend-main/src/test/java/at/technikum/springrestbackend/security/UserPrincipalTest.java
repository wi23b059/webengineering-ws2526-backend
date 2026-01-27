package at.technikum.springrestbackend.security;

import at.technikum.springrestbackend.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link UserPrincipal}.
 */

class UserPrincipalTest {

    @Test
    void constructor_shouldSetAllFieldsCorrectly() {
        // given
        UUID id = UUID.randomUUID();
        String username = "testuser";
        String password = "secret";
        Role role = Role.USER;

        // when
        UserPrincipal principal = new UserPrincipal(id, username, password, role);

        // then
        assertEquals(id, principal.getId());
        assertEquals(username, principal.getUsername());
        assertEquals(password, principal.getPassword());
        assertEquals(role, principal.getRole());
    }

    @Test
    void getAuthorities_shouldReturnRoleWithPrefixROLE_() {
        // given
        UserPrincipal principal = new UserPrincipal(
                UUID.randomUUID(),
                "admin",
                "pw",
                Role.ADMIN
        );

        // when
        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();

        // then
        assertEquals(1, authorities.size());
        assertEquals("ROLE_ADMIN", authorities.iterator().next().getAuthority());
    }

    @Test
    void accountFlags_shouldAlwaysReturnTrue() {
        // given
        UserPrincipal principal = new UserPrincipal(
                UUID.randomUUID(),
                "user",
                "pw",
                Role.USER
        );

        // then
        assertTrue(principal.isAccountNonExpired());
        assertTrue(principal.isAccountNonLocked());
        assertTrue(principal.isCredentialsNonExpired());
        assertTrue(principal.isEnabled());
    }
}
