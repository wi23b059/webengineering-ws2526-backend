package at.technikum.springrestbackend.security;

import at.technikum.springrestbackend.dto.OrderItemRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link AccessPermission}.
 */

class AccessPermissionTest {

    static class TestAccessPermission implements AccessPermission {

        @Override
        public boolean supports(Authentication authentication, String className) {
            return "User".equals(className);
        }

        @Override
        public boolean hasPermission(Authentication authentication, UUID resourceId) {
            return authentication != null && resourceId != null;
        }
    }

    @Test
    void supports_returnsTrue_forSupportedClass() {
        AccessPermission permission = new TestAccessPermission();
        Authentication auth = new UsernamePasswordAuthenticationToken("user", "password");

        boolean result = permission.supports(auth, "User");

        assertTrue(result);
    }

    @Test
    void supports_returnsFalse_forUnsupportedClass() {
        AccessPermission permission = new TestAccessPermission();
        Authentication auth = new UsernamePasswordAuthenticationToken("user", "password");

        boolean result = permission.supports(auth, "Order");

        assertFalse(result);
    }

    @Test
    void hasPermission_returnsTrue_whenAuthenticationAndResourceIdPresent() {
        AccessPermission permission = new TestAccessPermission();
        Authentication auth = new UsernamePasswordAuthenticationToken("user", "password");
        UUID resourceId = UUID.randomUUID();

        boolean result = permission.hasPermission(auth, resourceId);

        assertTrue(result);
    }

    @Test
    void hasPermission_returnsFalse_whenAuthenticationIsNull() {
        AccessPermission permission = new TestAccessPermission();
        UUID resourceId = UUID.randomUUID();

        boolean result = permission.hasPermission(null, resourceId);

        assertFalse(result);
    }
}
