package at.technikum.springrestbackend.security.jwt;

import at.technikum.springrestbackend.entity.Role;
import at.technikum.springrestbackend.security.UserPrincipal;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JwtToPrincipalConverter {

    public UserPrincipal convert(DecodedJWT decodedJWT) {
        UUID id = UUID.fromString(decodedJWT.getSubject());
        String username = decodedJWT.getClaim("username").asString();
        String roleStr = decodedJWT.getClaim("role").asString();

        Role role;
        try {
            role = Role.valueOf(roleStr);
        } catch (Exception ex) {
            // falls Claim fehlt oder ungültig ist: Fallback oder eigene Behandlung
            // z.B. Standard auf USER setzen oder IllegalArgumentException werfen
            role = Role.USER;
        }

        return new UserPrincipal(id, username, "", role);
    }
}
