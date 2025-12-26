package at.technikum.springrestbackend.security.jwt;
import at.technikum.springrestbackend.security.jwt.JwtProperties;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class JwtDecoderTest {

    private JwtProperties jwtProperties;
    private JwtDecoder jwtDecoder;

    @BeforeEach
    void setUp() {
        jwtProperties = mock(JwtProperties.class);
        when(jwtProperties.getSecret()).thenReturn("secret");
        jwtDecoder = new JwtDecoder(jwtProperties);
    }

    @Test
    void decode_shouldReturnDecodedJWT_whenTokenIsValid() {
        // Beispieltoken: erzeugt ein gültiges Token mit dem Secret
        String token = com.auth0.jwt.JWT.create()
                .withSubject("user")
                .sign(com.auth0.jwt.algorithms.Algorithm.HMAC256("secret"));

        DecodedJWT decodedJWT = jwtDecoder.decode(token);

        assertNotNull(decodedJWT);
        assertEquals("user", decodedJWT.getSubject());
    }

    @Test
    void decode_shouldThrowException_whenTokenIsInvalid() {
        String invalidToken = "invalid.token.here";

        assertThrows(com.auth0.jwt.exceptions.JWTVerificationException.class, () -> {
            jwtDecoder.decode(invalidToken);
        });
    }
}
