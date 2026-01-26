package at.technikum.springrestbackend.security.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.junit.jupiter.api.Assertions.*;

class JwtPropertiesTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withPropertyValues("security.jwt.secret=my-secret-key")
            .withUserConfiguration(JwtProperties.class);

    @Test
    void testJwtSecretInjection() {
        contextRunner.run(context -> {
            assertTrue(context.containsBean("jwtProperties"));
            JwtProperties props = context.getBean(JwtProperties.class);
            assertEquals("my-secret-key", props.getSecret());
        });
    }
}
