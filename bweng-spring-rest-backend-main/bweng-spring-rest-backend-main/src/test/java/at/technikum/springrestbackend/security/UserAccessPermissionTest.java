package at.technikum.springrestbackend.security;

import at.technikum.springrestbackend.entity.Role;
import at.technikum.springrestbackend.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserAccessPermissionTest {

    private UserAccessPermission permission;
    private Authentication authentication;
    private UserPrincipal principal;

    @BeforeEach
    void setUp() {
        permission = new UserAccessPermission();

        UUID userId = UUID.randomUUID();
        principal = new UserPrincipal(
                userId,
                "testuser",
                "password",
                Role.USER
        );

        authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(principal);
    }

    // ---------------- supports() ----------------

    @Test
    void supports_shouldReturnTrue_forUserClassName() {
        // when
        boolean result = permission.supports(authentication, User.class.getName());

        // then
        assertTrue(result);
    }

    @Test
    void supports_shouldReturnFalse_forOtherClassName() {
        // when
        boolean result = permission.supports(authentication, "java.lang.String");

        // then
        assertFalse(result);
    }

    // ---------------- hasPermission() ----------------

    @Test
    void hasPermission_shouldReturnTrue_whenUserIdMatchesResourceId() {
        // given
        UUID sameId = principal.getId();

        // when
        boolean result = permission.hasPermission(authentication, sameId);

        // then
        assertTrue(result);
    }

    @Test
    void hasPermission_shouldReturnFalse_whenUserIdDoesNotMatchResourceId() {
        // given
        UUID otherId = UUID.randomUUID();

        // when
        boolean result = permission.hasPermission(authentication, otherId);

        // then
        assertFalse(result);
    }
}
