package at.technikum.springrestbackend.security.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


// normally this test is not neccessary, bc it only tests the springboot-functionality

@SpringBootTest
class JwtPropertiesTest {

    @Autowired
    private JwtProperties jwtProperties;

    @Test
    void secretIsLoaded() {
        assertEquals("mysecretkey", jwtProperties.getSecret());
    }
}
