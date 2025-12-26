package at.technikum.springrestbackend.security.jwt;

import at.technikum.springrestbackend.entity.Role;
import at.technikum.springrestbackend.security.UserPrincipal;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtToPrincipalConverterTest {

    private JwtToPrincipalConverter converter;

    @BeforeEach
    void setUp() {
        converter = new JwtToPrincipalConverter();
    }

    @Test
    void convert_validJwt_returnsUserPrincipal() {
        UUID expectedId = UUID.randomUUID();
        String username = "testuser";
        String roleStr = "ADMIN";

        DecodedJWT decodedJWT = mock(DecodedJWT.class);
        Claim usernameClaim = mock(Claim.class);
        Claim roleClaim = mock(Claim.class);

        when(decodedJWT.getSubject()).thenReturn(expectedId.toString());
        when(decodedJWT.getClaim("username")).thenReturn(usernameClaim);
        when(decodedJWT.getClaim("role")).thenReturn(roleClaim);
        when(usernameClaim.asString()).thenReturn(username);
        when(roleClaim.asString()).thenReturn(roleStr);

        UserPrincipal principal = converter.convert(decodedJWT);

        assertEquals(expectedId, principal.getId());
        assertEquals(username, principal.getUsername());
        assertEquals(Role.ADMIN, principal.getRole());
        assertEquals("", principal.getPassword()); // JWT-basiert, daher leer
    }

    @Test
    void convert_invalidRole_defaultsToUser() {
        UUID expectedId = UUID.randomUUID();
        String username = "testuser";
        String invalidRole = "INVALID";

        DecodedJWT decodedJWT = mock(DecodedJWT.class);
        Claim usernameClaim = mock(Claim.class);
        Claim roleClaim = mock(Claim.class);

        when(decodedJWT.getSubject()).thenReturn(expectedId.toString());
        when(decodedJWT.getClaim("username")).thenReturn(usernameClaim);
        when(decodedJWT.getClaim("role")).thenReturn(roleClaim);
        when(usernameClaim.asString()).thenReturn(username);
        when(roleClaim.asString()).thenReturn(invalidRole);

        UserPrincipal principal = converter.convert(decodedJWT);

        assertEquals(Role.USER, principal.getRole()); // default
    }
}
