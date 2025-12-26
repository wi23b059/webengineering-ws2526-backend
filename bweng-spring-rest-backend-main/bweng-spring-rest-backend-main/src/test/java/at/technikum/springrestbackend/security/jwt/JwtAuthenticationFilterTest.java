package at.technikum.springrestbackend.security.jwt;

import at.technikum.springrestbackend.security.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    private final JwtDecoder jwtDecoder = mock(JwtDecoder.class);
    private final JwtToPrincipalConverter jwtToPrincipalConverter = mock(JwtToPrincipalConverter.class);
    private final JwtAuthenticationFilter filter =
            new JwtAuthenticationFilter(jwtDecoder, jwtToPrincipalConverter);

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_shouldAuthenticateWhenValidBearerTokenPresent() throws Exception {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        String token = "valid.jwt.token";

        DecodedJWT decodedJWT = mock(DecodedJWT.class);

        UserPrincipal principal = new UserPrincipal(
                UUID.randomUUID(),
                "user",
                "pw",
                at.technikum.springrestbackend.entity.Role.USER
        );

        when(request.getHeader(HttpHeaders.AUTHORIZATION))
                .thenReturn("Bearer " + token);
        when(jwtDecoder.decode(token)).thenReturn(decodedJWT);
        when(jwtToPrincipalConverter.convert(decodedJWT)).thenReturn(principal);

        // when
        filter.doFilter(request, response, filterChain);

        // then
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(principal,
                SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        verify(filterChain).doFilter(request, response);
    }


    @Test
    void doFilterInternal_shouldDoNothingWhenNoAuthorizationHeaderPresent() throws Exception {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

        // when
        filter.doFilter(request, response, filterChain);

        // then
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);

        verifyNoInteractions(jwtDecoder, jwtToPrincipalConverter);
    }

    @Test
    void doFilterInternal_shouldIgnoreNonBearerAuthorizationHeader() throws Exception {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(request.getHeader(HttpHeaders.AUTHORIZATION))
                .thenReturn("Basic abc123");

        // when
        filter.doFilter(request, response, filterChain);

        // then
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);

        verifyNoInteractions(jwtDecoder, jwtToPrincipalConverter);
    }

    @Test
    void shouldNotFilter_shouldReturnTrueForUserRegistrationEndpoint() throws Exception {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getServletPath()).thenReturn("/api/users");
        when(request.getMethod()).thenReturn("POST");

        // when & then
        assertTrue(filter.shouldNotFilter(request));
    }

    @Test
    void shouldNotFilter_shouldReturnFalseForOtherRequests() throws Exception {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getServletPath()).thenReturn("/api/other");
        when(request.getMethod()).thenReturn("GET");

        // when & then
        assertFalse(filter.shouldNotFilter(request));
    }
}
