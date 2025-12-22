package at.technikum.springrestbackend.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link OrderItemRequestDto}.
 */
class OrderItemRequestDtoTest {

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
        OrderItemRequestDto dto = OrderItemRequestDto.builder()
                .productId(1)
                .quantity(2)
                .price(BigDecimal.valueOf(9.99))
                .build();

        Set<ConstraintViolation<OrderItemRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void nullProductIdFailsValidation() {
        OrderItemRequestDto dto = OrderItemRequestDto.builder()
                .productId(null)
                .quantity(1)
                .price(BigDecimal.ONE)
                .build();

        Set<ConstraintViolation<OrderItemRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next()
                .getPropertyPath().toString()).isEqualTo("productId");
    }

    @Test
    void nullQuantityFailsValidation() {
        OrderItemRequestDto dto = OrderItemRequestDto.builder()
                .productId(1)
                .quantity(null)
                .price(BigDecimal.ONE)
                .build();

        Set<ConstraintViolation<OrderItemRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next()
                .getPropertyPath().toString()).isEqualTo("quantity");
    }

    @Test
    void nonPositiveQuantityFailsValidation() {
        OrderItemRequestDto dto = OrderItemRequestDto.builder()
                .productId(1)
                .quantity(0)
                .price(BigDecimal.ONE)
                .build();

        Set<ConstraintViolation<OrderItemRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next()
                .getPropertyPath().toString()).isEqualTo("quantity");
    }

    @Test
    void nullPriceFailsValidation() {
        OrderItemRequestDto dto = OrderItemRequestDto.builder()
                .productId(1)
                .quantity(1)
                .price(null)
                .build();

        Set<ConstraintViolation<OrderItemRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next()
                .getPropertyPath().toString()).isEqualTo("price");
    }

    @Test
    void nonPositivePriceFailsValidation() {
        OrderItemRequestDto dto = OrderItemRequestDto.builder()
                .productId(1)
                .quantity(1)
                .price(BigDecimal.ZERO)
                .build();

        Set<ConstraintViolation<OrderItemRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next()
                .getPropertyPath().toString()).isEqualTo("price");
    }

    // -------------------------------------------------------------------------
    // Lombok
    // -------------------------------------------------------------------------

    @Test
    void getterAndSetterWorkCorrectly() {
        OrderItemRequestDto dto = new OrderItemRequestDto();
        dto.setProductId(5);
        dto.setQuantity(3);
        dto.setPrice(BigDecimal.valueOf(4.50));

        assertThat(dto.getProductId()).isEqualTo(5);
        assertThat(dto.getQuantity()).isEqualTo(3);
        assertThat(dto.getPrice()).isEqualTo(BigDecimal.valueOf(4.50));
    }

    @Test
    void allArgsConstructorCreatesCorrectObject() {
        OrderItemRequestDto dto = new OrderItemRequestDto(
                2,
                4,
                BigDecimal.valueOf(12.99)
        );

        assertThat(dto.getProductId()).isEqualTo(2);
        assertThat(dto.getQuantity()).isEqualTo(4);
        assertThat(dto.getPrice()).isEqualTo(BigDecimal.valueOf(12.99));
    }

    @Test
    void builderCreatesCorrectObject() {
        OrderItemRequestDto dto = OrderItemRequestDto.builder()
                .productId(9)
                .quantity(1)
                .price(BigDecimal.valueOf(99.99))
                .build();

        assertThat(dto.getProductId()).isEqualTo(9);
        assertThat(dto.getQuantity()).isEqualTo(1);
        assertThat(dto.getPrice()).isEqualTo(BigDecimal.valueOf(99.99));
    }
}
