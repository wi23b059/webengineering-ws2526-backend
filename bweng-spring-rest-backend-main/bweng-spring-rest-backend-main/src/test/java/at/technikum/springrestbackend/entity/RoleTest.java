package at.technikum.springrestbackend.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Role}.
 */

class RoleTest {

    @Test
    void shouldContainUserAndAdminRoles() {
        Role[] roles = Role.values();

        assertEquals(2, roles.length);
        assertTrue(containsRole(roles, Role.USER));
        assertTrue(containsRole(roles, Role.ADMIN));
    }

    @Test
    void valueOf_shouldReturnCorrectEnum() {
        assertEquals(Role.USER, Role.valueOf("USER"));
        assertEquals(Role.ADMIN, Role.valueOf("ADMIN"));
    }

    @Test
    void valueOf_shouldThrowExceptionForInvalidValue() {
        assertThrows(IllegalArgumentException.class, () ->
                Role.valueOf("SUPERADMIN")
        );
    }

    private boolean containsRole(Role[] roles, Role expected) {
        for (Role role : roles) {
            if (role == expected) {
                return true;
            }
        }
        return false;
    }
}
