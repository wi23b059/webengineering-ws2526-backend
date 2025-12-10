package at.technikum.springrestbackend.security;

import at.technikum.springrestbackend.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Permission evaluator for User resources.
 * <p>
 * Allows access only if the authenticated user's ID matches
 * the ID of the User resource being accessed.
 */
@Component
public class UserAccessPermission implements AccessPermission {

    /**
     * Indicates that this permission evaluator applies to User entities.
     *
     * @param authentication the current authentication
     * @param className the name of the target class
     * @return true if the class is User, false otherwise
     */
    @Override
    public boolean supports(Authentication authentication, String className) {
        return className.equals(User.class.getName());
    }

    /**
     * Checks whether the authenticated user has access to the User resource.
     * <p>
     * A user can only access their own User record (matching IDs).
     *
     * @param authentication the current authentication
     * @param resourceId the ID of the User being accessed
     * @return true if the user owns the resource, false otherwise
     */
    @Override
    public boolean hasPermission(Authentication authentication, UUID resourceId) {
        return ((UserPrincipal) authentication.getPrincipal()).getId().equals(resourceId);
    }
}
