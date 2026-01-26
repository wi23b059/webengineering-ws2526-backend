package at.technikum.springrestbackend.config;

import at.technikum.springrestbackend.security.jwt.JwtAuthenticationFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityConfigTest {

    private JwtAuthenticationFilter jwtFilter;
    private SecurityConfig securityConfig;

    @BeforeEach
    void setUp() {
        jwtFilter = mock(JwtAuthenticationFilter.class);
        securityConfig = new SecurityConfig(jwtFilter);
    }

    @Test
    void testPasswordEncoder() {
        PasswordEncoder encoder = securityConfig.passwordEncoder();
        assertNotNull(encoder);
        assertTrue(encoder.matches("raw", encoder.encode("raw")));
    }

    @Test
    void testAuthenticationProvider() {
        var userDetailsService = mock(org.springframework.security.core.userdetails.UserDetailsService.class);
        AuthenticationProvider provider = securityConfig.authenticationProvider(userDetailsService);
        assertNotNull(provider);
    }

    @Test
    void testCorsConfigurer() {
        WebMvcConfigurer mvc = securityConfig.corsConfigurer();
        assertNotNull(mvc);
    }

    @Test
    void testSecurityFilterChainIsNotNull() throws Exception {
        // Wir können HttpSecurity nur mocken, um die Methode aufzurufen
        var httpSecurity = mock(org.springframework.security.config.annotation.web.builders.HttpSecurity.class, Mockito.RETURNS_DEEP_STUBS);

        // Aufruf testet nur, dass die Methode läuft
        SecurityFilterChain chain = securityConfig.securityFilterChain(httpSecurity);
        assertNotNull(chain);
    }

    @Test
    void testAuthenticationManager() throws Exception {
        var httpSecurity = mock(org.springframework.security.config.annotation.web.builders.HttpSecurity.class, RETURNS_DEEP_STUBS);
        var userDetailsService = mock(org.springframework.security.core.userdetails.UserDetailsService.class);

        AuthenticationManager manager = securityConfig.authenticationManager(httpSecurity, userDetailsService);
        assertNotNull(manager);
    }
}
