package at.technikum.springrestbackend.security.jwt;

import at.technikum.springrestbackend.entity.Role;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class JwtIssuerTest {

    @Test
    void issue_shouldReturnToken() {
        JwtProperties props = Mockito.mock(JwtProperties.class);
        Mockito.when(props.getSecret()).thenReturn("mysecret");

        JwtIssuer issuer = new JwtIssuer(props);
        String token = issuer.issue(UUID.randomUUID(), "bob123", Role.USER);

        assertNotNull(token);
    }
}
