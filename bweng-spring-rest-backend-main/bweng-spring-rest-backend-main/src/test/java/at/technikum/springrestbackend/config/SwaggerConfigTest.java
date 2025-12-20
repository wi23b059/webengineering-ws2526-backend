package at.technikum.springrestbackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link SwaggerConfig}.
 * Verifies OpenAPI bean creation and JWT security configuration.
 */
@SpringBootTest
class SwaggerConfigTest {

    @Autowired
    private OpenAPI openAPI;

    @Test
    void openApiBeanExists() {
        assertThat(openAPI).isNotNull();
    }

    @Test
    void securityRequirementsAreConfigured() {
        assertThat(openAPI.getSecurity()).isNotNull();
        assertThat(openAPI.getSecurity()).isNotEmpty();

        SecurityRequirement requirement = openAPI.getSecurity().get(0);

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
