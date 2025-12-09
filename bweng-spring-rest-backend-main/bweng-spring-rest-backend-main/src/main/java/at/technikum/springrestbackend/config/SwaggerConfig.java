package at.technikum.springrestbackend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures Swagger/OpenAPI to support JWT authentication.
 * <p>
 * Adds security schemes so Swagger UI can send JWTs when testing protected endpoints.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Sets up Swagger's OpenAPI definition with JWT-based authentication.
     * <p>
     * Registers two ways to send JWTs: as a Bearer token or as a cookie.
     *
     * @return the configured OpenAPI object
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList("Bearer Token")
                        .addList("API Cookie")
                )
                .components(new Components()
                        .addSecuritySchemes("Bearer Token", apiJwtBearerScheme())
                        .addSecuritySchemes("API Cookie", apiCookieScheme())
                );
    }

    /**
     * Defines the Bearer token authentication scheme for Swagger UI.
     *
     * @return a security scheme using the Authorization header with a JWT
     */
    private SecurityScheme apiJwtBearerScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    /**
     * Defines the cookie-based authentication scheme for Swagger UI.
     *
     * @return a security scheme using a cookie named "JWT"
     */
    private SecurityScheme apiCookieScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.COOKIE)
                .name("JWT");
    }
}
