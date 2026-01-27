package at.technikum.springrestbackend.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link PermissionConfiguration}.
 */

class PermissionConfigurationTest {

    @Test
    void methodSecurityExpressionHandler_shouldUseCustomPermissionEvaluator() throws Exception {
        // given
        AccessPermissionEvaluator permissionEvaluator = mock(AccessPermissionEvaluator.class);
        PermissionConfiguration configuration = new PermissionConfiguration(permissionEvaluator);

        // when
        MethodSecurityExpressionHandler handler =
                configuration.methodSecurityExpressionHandler();

        // then
        assertNotNull(handler);
        assertTrue(handler instanceof DefaultMethodSecurityExpressionHandler);

        DefaultMethodSecurityExpressionHandler defaultHandler =
                (DefaultMethodSecurityExpressionHandler) handler;

        // Zugriff auf protected field via Reflection
        Field field = defaultHandler.getClass()
                .getSuperclass()
                .getDeclaredField("permissionEvaluator");

        field.setAccessible(true);
        Object actualEvaluator = field.get(defaultHandler);

        assertSame(permissionEvaluator, actualEvaluator);
    }
}
