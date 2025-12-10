package at.technikum.springrestbackend.security.jwt;

import at.technikum.springrestbackend.security.UserPrincipal;
import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * Authentication token used for JWT-based authentication.
 * <p>
 * Contains a UserPrincipal and marks the user as authenticated without
 * requiring credentials.
 */
public class UserPrincipalAuthenticationToken extends AbstractAuthenticationToken {

    private final UserPrincipal userPrincipal;

    /**
     * Creates an authenticated token for the given user principal.
     *
     * @param userPrincipal the authenticated user's details
     */
    public UserPrincipalAuthenticationToken(UserPrincipal userPrincipal) {
        super(userPrincipal.getAuthorities());
        this.userPrincipal = userPrincipal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userPrincipal;
    }

}
