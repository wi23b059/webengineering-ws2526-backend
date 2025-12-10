package at.technikum.springrestbackend.security;

import org.springframework.security.core.Authentication;

import java.util.UUID;

/**
 * Defines a contract for checking access permissions on specific resources.
 * <p>
 * Implementations of this interface provide permission logic for different
 * domain types (e.g., User, Cart, Order). Used by {@link AccessPermissionEvaluator}
 * to determine whether the authenticated user is allowed to access a resource.
 */
public interface AccessPermission {
    boolean supports(Authentication authentication, String className);
    boolean hasPermission(Authentication authentication, UUID resourceId);
}
