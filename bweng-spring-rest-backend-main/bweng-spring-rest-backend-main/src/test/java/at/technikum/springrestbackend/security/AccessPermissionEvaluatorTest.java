package at.technikum.springrestbackend.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccessPermissionEvaluatorTest {

    /**
     * Simple AccessPermission stub for testing.
     */
    static class TestAccessPermission implements AccessPermission {

        private final boolean supports;
        private final boolean grantsPermission;

        TestAccessPermission(boolean supports, boolean grantsPermission) {
            this.supports = supports;
            this.grantsPermission = grantsPermission;
        }

        @Override
        public boolean supports(Authentication authentication, String className) {
            return supports;
        }

        @Override
        public boolean hasPermission(Authentication authentication, UUID resourceId) {
            return grantsPermission;
        }
    }

    @Test
    void hasPermission_returnsTrue_whenAnyPermissionGrantsAccess() {
        Authentication auth = new UsernamePasswordAuthenticationToken("user", "password");
        UUID resourceId = UUID.randomUUID();

        AccessPermission grantingPermission =
                new TestAccessPermission(true, true);
        AccessPermission denyingPermission =
                new TestAccessPermission(true, false);

        AccessPermissionEvaluator evaluator =
                new AccessPermissionEvaluator(List.of(denyingPermission, grantingPermission));

        boolean result = evaluator.hasPermission(
                auth,
                (Serializable) resourceId,
                "User",
                null
        );

        assertTrue(result);
    }

    @Test
    void hasPermission_returnsFalse_whenNoPermissionGrantsAccess() {
        Authentication auth = new UsernamePasswordAuthenticationToken("user", "password");
        UUID resourceId = UUID.randomUUID();

        AccessPermission permission1 =
                new TestAccessPermission(true, false);
        AccessPermission permission2 =
                new TestAccessPermission(false, true);

        AccessPermissionEvaluator evaluator =
                new AccessPermissionEvaluator(List.of(permission1, permission2));

        boolean result = evaluator.hasPermission(
                auth,
                (Serializable) resourceId,
                "User",
                null
        );

        assertFalse(result);
    }

    @Test
    void hasPermission_returnsFalse_whenNoPermissionSupportsTargetType() {
        Authentication auth = new UsernamePasswordAuthenticationToken("user", "password");
        UUID resourceId = UUID.randomUUID();

        AccessPermission permission =
                new TestAccessPermission(false, true);

        AccessPermissionEvaluator evaluator =
                new AccessPermissionEvaluator(List.of(permission));

        boolean result = evaluator.hasPermission(
                auth,
                (Serializable) resourceId,
                "Order",
                null
        );

        assertFalse(result);
    }

    @Test
    void hasPermission_objectOverload_returnsFalse() {
        Authentication auth = new UsernamePasswordAuthenticationToken("user", "password");

        AccessPermissionEvaluator evaluator =
                new AccessPermissionEvaluator(List.of());

        boolean result = evaluator.hasPermission(auth, new Object(), "read");

        assertFalse(result);
    }
}
