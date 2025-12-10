package at.technikum.springrestbackend.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Verifies and decodes JWT tokens.
 * <p>
 * Uses the application's configured secret key to validate the token's
 * signature and expiration. Returns a {@link DecodedJWT} upon successful
 * verification, or throws an exception if the token is invalid.
 */
@Component
@RequiredArgsConstructor
public class JwtDecoder {
    private final JwtProperties jwtProperties;

    /**
     * Verifies the JWT's signature and parses its claims.
     *
     * @param token the raw JWT string (without "Bearer ")
     * @return a decoded JWT containing the token's claims
     */
    public DecodedJWT decode(String token) {
        return JWT.require(Algorithm.HMAC256(jwtProperties.getSecret()))
                .build().verify(token);
    }

}
