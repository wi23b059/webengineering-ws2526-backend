package at.technikum.springrestbackend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * Configures method-level security with a custom permission evaluator.
 * <p>
 * Registers an expression handler that delegates permission checks
 * to the application's {@link AccessPermissionEvaluator}.
 */
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class PermissionConfiguration {
    private final AccessPermissionEvaluator accessPermissionEvaluator;

    /**
     * Creates the method security expression handler and connects it
     * to the custom {@link AccessPermissionEvaluator}.
     *
     * @return the configured method security expression handler
     */
    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
        DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler = new DefaultMethodSecurityExpressionHandler();
        defaultMethodSecurityExpressionHandler.setPermissionEvaluator(accessPermissionEvaluator);
        return defaultMethodSecurityExpressionHandler;
    }
}
