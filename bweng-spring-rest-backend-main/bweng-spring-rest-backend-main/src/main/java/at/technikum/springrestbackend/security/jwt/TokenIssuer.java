    package at.technikum.springrestbackend.security.jwt;

    import at.technikum.springrestbackend.entity.Role;

    import java.util.UUID;

    /**
     * Defines the contract for generating JWT tokens.
     * <p>
     * Implementations of this interface produce signed JWT strings that
     * encode the authenticated user's ID, username and role.
     */
    public interface TokenIssuer {
        String issue(UUID userId, String username, Role role);
    }