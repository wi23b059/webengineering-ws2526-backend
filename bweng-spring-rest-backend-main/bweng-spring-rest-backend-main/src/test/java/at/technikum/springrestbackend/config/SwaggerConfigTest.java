package at.technikum.springrestbackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link SwaggerConfig}.
 * Verifies OpenAPI bean creation and JWT security configuration.
 */
@SpringJUnitConfig
@Import(SwaggerConfig.class) // Nur die SwaggerConfig laden
class SwaggerConfigTest {

    @MockitoBean
    private OpenAPI openAPI;

    @Test
    void openApiBeanExists() {
        SwaggerConfig config = new SwaggerConfig();
        OpenAPI openAPI = config.openAPI();
        assertThat(openAPI).isNotNull();
    }

    @Test
    void securityRequirementsAreConfigured() {
        assertThat(openAPI.getSecurity()).isNotNull();
        assertThat(openAPI.getSecurity()).isNotEmpty();

        SecurityRequirement requirement = openAPI.getSecurity().getFirst();
        assertThat(requirement).containsKeys("Bearer Token", "API Cookie");
    }

    @Test
    void bearerTokenSecuritySchemeIsConfiguredCorrectly() {
        Components components = openAPI.getComponents();
        assertThat(components).isNotNull();

        SecurityScheme bearerScheme =
                components.getSecuritySchemes().get("Bearer Token");

        assertThat(bearerScheme).isNotNull();
        assertThat(bearerScheme.getType())
                .isEqualTo(SecurityScheme.Type.HTTP);
        assertThat(bearerScheme.getScheme())
                .isEqualTo("bearer");
        assertThat(bearerScheme.getBearerFormat())
                .isEqualTo("JWT");
    }

    @Test
    void cookieSecuritySchemeIsConfiguredCorrectly() {
        Components components = openAPI.getComponents();
        assertThat(components).isNotNull();

        SecurityScheme cookieScheme =
                components.getSecuritySchemes().get("API Cookie");

        assertThat(cookieScheme).isNotNull();
        assertThat(cookieScheme.getType())
                .isEqualTo(SecurityScheme.Type.APIKEY);
        assertThat(cookieScheme.getIn())
                .isEqualTo(SecurityScheme.In.COOKIE);
        assertThat(cookieScheme.getName())
                .isEqualTo("JWT");
    }
}
