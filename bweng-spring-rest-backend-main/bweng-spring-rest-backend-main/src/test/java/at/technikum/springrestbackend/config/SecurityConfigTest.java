package at.technikum.springrestbackend.config;

import at.technikum.springrestbackend.security.jwt.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit- and slice-tests for {@link SecurityConfig}.
 * Compatible with Spring Boot 3.4+ (uses @MockitoBean).
 */
@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private MockMvc mockMvc;

    /**
     * Mocked to avoid loading real JWT dependencies.
     */
    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    // -------------------------------------------------------------------------
    // Bean tests
    // -------------------------------------------------------------------------

    @Test
    void securityFilterChainBeanExists() {
        SecurityFilterChain filterChain =
                applicationContext.getBean(SecurityFilterChain.class);

        assertThat(filterChain).isNotNull();
    }

    @Test
    void passwordEncoderBeanExistsAndUsesBCrypt() {
        PasswordEncoder encoder =
                applicationContext.getBean(PasswordEncoder.class);

        assertThat(encoder).isNotNull();
        assertThat(encoder.getClass().getSimpleName())
                .isEqualTo("BCryptPasswordEncoder");
    }

    @Test
    void authenticationProviderBeanExists() {
        AuthenticationProvider provider =
                applicationContext.getBean(AuthenticationProvider.class);

        assertThat(provider).isNotNull();
    }

    @Test
    void authenticationManagerBeanExists() {
        AuthenticationManager manager =
                applicationContext.getBean(AuthenticationManager.class);

        assertThat(manager).isNotNull();
    }

    // -------------------------------------------------------------------------
    // Endpoint security tests
    // -------------------------------------------------------------------------

    @Test
    void authEndpointsArePublic() throws Exception {
        mockMvc.perform(get("/auth/test"))
                .andExpect(status().isNotFound());
    }

    @Test
    void apiEndpointsArePublicDuringDevelopment() throws Exception {
        mockMvc.perform(get("/api/test"))
                .andExpect(status().isNotFound());
    }

    @Test
    void otherEndpointsRequireAuthentication() throws Exception {
        mockMvc.perform(get("/secure/test"))
                .andExpect(status().isUnauthorized());
    }

    // -------------------------------------------------------------------------
    // CORS tests
    // -------------------------------------------------------------------------

    @Test
    void corsAllowsFrontendOrigin() throws Exception {
        mockMvc.perform(get("/api/test")
                .header("Origin", "http://localhost:5173")
                .header("Access-Control-Request-Method", "GET"))
                .andExpect(status().isNotFound());
    }
}
