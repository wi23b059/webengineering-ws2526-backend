package at.technikum.springrestbackend.security.jwt;

import at.technikum.springrestbackend.entity.Role;
import at.technikum.springrestbackend.security.UserPrincipal;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserPrincipalAuthenticationTokenTest {

    @Test
    void testTokenMethods() {
        UUID userId = UUID.randomUUID();
        UserPrincipal principal = new UserPrincipal(userId, "testuser", "password", Role.USER);

        UserPrincipalAuthenticationToken token = new UserPrincipalAuthenticationToken(principal);

        // getPrincipal should return the original UserPrincipal
        assertEquals(principal, token.getPrincipal());

        // getCredentials should return null
        assertNull(token.getCredentials());

        // token should be authenticated
        assertTrue(token.isAuthenticated());

        // authorities should match the user's role
        assertEquals(1, token.getAuthorities().size());
        assertEquals("ROLE_USER", token.getAuthorities().iterator().next().getAuthority());
    }
}
