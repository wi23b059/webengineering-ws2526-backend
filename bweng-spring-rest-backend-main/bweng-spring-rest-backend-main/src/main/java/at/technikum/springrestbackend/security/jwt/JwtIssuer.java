package at.technikum.springrestbackend.security.jwt;

import at.technikum.springrestbackend.entity.Role;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * Generates signed JWT tokens for authenticated users.
 * <p>
 * Builds a token containing the user's ID, username, and role,
 * sets an expiration time, and signs it using the application's
 * configured secret key.
 */
@Component
public class JwtIssuer implements TokenIssuer {
    private final JwtProperties jwtProperties;

    public JwtIssuer(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * Issues a new JWT for the given user.
     *
     * @param userId the user's UUID
     * @param username the user's username
     * @param role the user's role
     * @return a signed JWT token as a string
     */
    @Override
    public String issue(UUID userId, String username, Role role) {
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
                .withClaim("username", username)
                .withClaim("role", role.name())
                .sign(Algorithm.HMAC256(jwtProperties.getSecret()));
    }
}
