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
 * Unit tests for {@link CartItemRequestDto}.
 */
class CartItemRequestDtoTest {

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
    void validDtoPassesValidation() {
        CartItemRequestDto dto = CartItemRequestDto.builder()
                .productId(1)
                .quantity(2)
                .build();

        Set<ConstraintViolation<CartItemRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void nullProductIdFailsValidation() {
        CartItemRequestDto dto = CartItemRequestDto.builder()
                .productId(null)
                .quantity(2)
                .build();

        Set<ConstraintViolation<CartItemRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next()
                .getPropertyPath().toString()).isEqualTo("productId");
    }

    @Test
    void nonPositiveQuantityFailsValidation() {
        CartItemRequestDto dto = CartItemRequestDto.builder()
                .productId(1)
                .quantity(0)
                .build();

        Set<ConstraintViolation<CartItemRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next()
                .getPropertyPath().toString()).isEqualTo("quantity");
    }

    // -------------------------------------------------------------------------
    // Lombok
    // -------------------------------------------------------------------------

    @Test
    void getterAndSetterWorkCorrectly() {
        CartItemRequestDto dto = new CartItemRequestDto();
        dto.setProductId(5);
        dto.setQuantity(3);

        assertThat(dto.getProductId()).isEqualTo(5);
        assertThat(dto.getQuantity()).isEqualTo(3);
    }

    @Test
    void builderCreatesCorrectObject() {
        CartItemRequestDto dto = CartItemRequestDto.builder()
                .productId(10)
                .quantity(1)
                .build();

        assertThat(dto.getProductId()).isEqualTo(10);
        assertThat(dto.getQuantity()).isEqualTo(1);
    }
}
