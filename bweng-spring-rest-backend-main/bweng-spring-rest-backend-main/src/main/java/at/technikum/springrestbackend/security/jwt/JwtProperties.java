package at.technikum.springrestbackend.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for JWT handling.
 * <p>
 * Reads the JWT settings from the application's configuration
 * under the prefix {@code security.jwt}, such as the signing secret.
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties("security.jwt")
public class JwtProperties {
    private String secret;
}
