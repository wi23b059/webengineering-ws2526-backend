package at.technikum.springrestbackend.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link TokenRequestDto}.
 */
class TokenRequestDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // -------------------------------------------------------------------------
    // Validation
    // -------------------------------------------------------------------------

    @Test
    void validDtoPassesValidation() {
        TokenRequestDto dto = new TokenRequestDto();
        dto.setUsername("testuser");
        dto.setPassword("password123");

        Set<ConstraintViolation<TokenRequestDto>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void blankFieldsFailValidation() {
        TokenRequestDto dto = new TokenRequestDto();
        dto.setUsername("");  // blank
        dto.setPassword("");  // blank

        Set<ConstraintViolation<TokenRequestDto>> violations = validator.validate(dto);

        assertThat(violations).hasSizeGreaterThanOrEqualTo(2);
    }

    // -------------------------------------------------------------------------
    // Lombok
    // -------------------------------------------------------------------------

    @Test
    void getterAndSetterWorkCorrectly() {
        TokenRequestDto dto = new TokenRequestDto();
        dto.setUsername("user1");
        dto.setPassword("secret");

        assertThat(dto.getUsername()).isEqualTo("user1");
        assertThat(dto.getPassword()).isEqualTo("secret");
    }
}
