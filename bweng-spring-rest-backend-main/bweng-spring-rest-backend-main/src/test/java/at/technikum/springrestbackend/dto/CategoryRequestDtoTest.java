package at.technikum.springrestbackend.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link CategoryRequestDto}.
 */
class CategoryRequestDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // -------------------------------------------------------------------------
    // Validation
    // -------------------------------------------------------------------------

    @Test
    void validCategoryNamePassesValidation() {
        CategoryRequestDto dto = new CategoryRequestDto("Electronics");

        Set<ConstraintViolation<CategoryRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void blankNameFailsValidation() {
        CategoryRequestDto dto = new CategoryRequestDto("");

        Set<ConstraintViolation<CategoryRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations).hasSize(1);
        ConstraintViolation<CategoryRequestDto> violation = violations.iterator().next();

        assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
        assertThat(violation.getMessage()).isEqualTo("Name is required");
    }

    @Test
    void nullNameFailsValidation() {
        CategoryRequestDto dto = new CategoryRequestDto(null);

        Set<ConstraintViolation<CategoryRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next()
                .getPropertyPath().toString()).isEqualTo("name");
    }

    @Test
    void nameLongerThan90CharactersFailsValidation() {
        String longName = "a".repeat(91);
        CategoryRequestDto dto = new CategoryRequestDto(longName);

        Set<ConstraintViolation<CategoryRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations).hasSize(1);
        ConstraintViolation<CategoryRequestDto> violation = violations.iterator().next();

        assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
        assertThat(violation.getMessage()).isEqualTo("Name must be at most 90 characters");
    }

    // -------------------------------------------------------------------------
    // Lombok
    // -------------------------------------------------------------------------

    @Test
    void getterAndSetterWorkCorrectly() {
        CategoryRequestDto dto = new CategoryRequestDto();
        dto.setName("Books");

        assertThat(dto.getName()).isEqualTo("Books");
    }

    @Test
    void allArgsConstructorCreatesCorrectObject() {
        CategoryRequestDto dto = new CategoryRequestDto("Clothing");

        assertThat(dto.getName()).isEqualTo("Clothing");
    }
}
