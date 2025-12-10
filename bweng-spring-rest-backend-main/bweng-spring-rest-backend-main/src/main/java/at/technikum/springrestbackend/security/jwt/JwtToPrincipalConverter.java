package at.technikum.springrestbackend.security.jwt;

import at.technikum.springrestbackend.entity.Role;
import at.technikum.springrestbackend.security.UserPrincipal;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Converts a decoded JWT into a UserPrincipal.
 * <p>
 * Extracts the user's ID, username and role from the JWT claims
 * and reconstructs a UserPrincipal that Spring Security can use
 * for authentication and authorization.
 */
@Component
public class JwtToPrincipalConverter {

    /**
     * Converts the given decoded JWT into a UserPrincipal.
     *
     * @param decodedJWT the decoded and verified JWT
     * @return a UserPrincipal built from the JWT claims
     */
    public UserPrincipal convert(DecodedJWT decodedJWT) {
        UUID id = UUID.fromString(decodedJWT.getSubject());
        String username = decodedJWT.getClaim("username").asString();
        String roleStr = decodedJWT.getClaim("role").asString();

        Role role;
        try {
            role = Role.valueOf(roleStr);
        } catch (Exception ex) {
            // default to USER if role is missing or invalid
            role = Role.USER;
        }

        // password is empty because JWT-based authentication does not use passwords
        return new UserPrincipal(id, username, "", role);
    }
}
