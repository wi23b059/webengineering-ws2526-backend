package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.entity.Salutation;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link UserRequestDto}.
 */
class UserRequestDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // -------------------------------------------------------------------------
    // Valid DTO
    // -------------------------------------------------------------------------

    @Test
    void validDtoPassesValidation() {
        UserRequestDto dto = new UserRequestDto(
                Salutation.MR,
                "John",
                "Doe",
                "AT",
                "Street 1",
                "Vienna",
                "1010",
                "john.doe@example.com",
                "johndoe",
                "Password1",
                null
        );

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    // -------------------------------------------------------------------------
    // Invalid fields
    // -------------------------------------------------------------------------

    @Test
    void nullOrBlankFieldsFailValidation() {
        UserRequestDto dto = new UserRequestDto(
                null, // salutation
                "",   // firstName
                "",   // lastName
                "A",  // invalid country code
                "",   // address
                "",   // city
                "",   // zip
                "invalid-email", // email
                "",   // username
                "pass", // password too weak
                ""     // profilePicturePath fine
        );

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        assertThat(violations).hasSizeGreaterThanOrEqualTo(9); // at least 9 violations expected
    }

    // -------------------------------------------------------------------------
    // Lombok
    // -------------------------------------------------------------------------

    @Test
    void gettersAndSettersWorkCorrectly() {
        UserRequestDto dto = new UserRequestDto();
        dto.setSalutation(Salutation.MR);
        dto.setFirstName("Alice");
        dto.setLastName("Smith");
        dto.setCountryCode("DE");
        dto.setAddress("Main Street");
        dto.setCity("Berlin");
        dto.setZip("12345");
        dto.setEmail("alice@example.com");
        dto.setUsername("alice123");
        dto.setPassword("Password1");
        dto.setProfilePicturePath("/img/alice.png");

        assertThat(dto.getSalutation()).isEqualTo(Salutation.MR);
        assertThat(dto.getFirstName()).isEqualTo("Alice");
        assertThat(dto.getLastName()).isEqualTo("Smith");
        assertThat(dto.getCountryCode()).isEqualTo("DE");
        assertThat(dto.getAddress()).isEqualTo("Main Street");
        assertThat(dto.getCity()).isEqualTo("Berlin");
        assertThat(dto.getZip()).isEqualTo("12345");
        assertThat(dto.getEmail()).isEqualTo("alice@example.com");
        assertThat(dto.getUsername()).isEqualTo("alice123");
        assertThat(dto.getPassword()).isEqualTo("Password1");
        assertThat(dto.getProfilePicturePath()).isEqualTo("/img/alice.png");
    }
}
