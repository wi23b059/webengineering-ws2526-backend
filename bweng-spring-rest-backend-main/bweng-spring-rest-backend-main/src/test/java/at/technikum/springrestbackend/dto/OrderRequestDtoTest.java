package at.technikum.springrestbackend.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link OrderRequestDto}.
 */
class OrderRequestDtoTest {

    private Validator validator;
    private final ObjectMapper objectMapper = new ObjectMapper();

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
        OrderRequestDto dto = OrderRequestDto.builder()
                .userId("user-123")
                .totalPrice(BigDecimal.valueOf(99.90))
                .paymentMethod("CREDIT_CARD")
                .build();

        Set<ConstraintViolation<OrderRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void nullUserIdFailsValidation() {
        OrderRequestDto dto = OrderRequestDto.builder()
                .userId(null)
                .totalPrice(BigDecimal.valueOf(50))
                .build();

        Set<ConstraintViolation<OrderRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString())
                .isEqualTo("userId");
    }

    @Test
    void nullTotalPriceFailsValidation() {
        OrderRequestDto dto = OrderRequestDto.builder()
                .userId("user-1")
                .totalPrice(null)
                .build();

        Set<ConstraintViolation<OrderRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString())
                .isEqualTo("totalPrice");
    }

    // -------------------------------------------------------------------------
    // Lombok
    // -------------------------------------------------------------------------

    @Test
    void allArgsConstructorCreatesCorrectObject() {
        OrderRequestDto dto = new OrderRequestDto(
                "user-42",
                BigDecimal.valueOf(120.00),
                "PAYPAL"
        );

        assertThat(dto.getUserId()).isEqualTo("user-42");
        assertThat(dto.getTotalPrice()).isEqualTo(BigDecimal.valueOf(120.00));
        assertThat(dto.getPaymentMethod()).isEqualTo("PAYPAL");
    }

    @Test
    void builderCreatesCorrectObject() {
        OrderRequestDto dto = OrderRequestDto.builder()
                .userId("builder-user")
                .totalPrice(BigDecimal.valueOf(75.50))
                .paymentMethod("INVOICE")
                .build();

        assertThat(dto.getUserId()).isEqualTo("builder-user");
        assertThat(dto.getTotalPrice()).isEqualTo(BigDecimal.valueOf(75.50));
        assertThat(dto.getPaymentMethod()).isEqualTo("INVOICE");
    }

    // -------------------------------------------------------------------------
    // JSON Serialization
    // -------------------------------------------------------------------------

    @Test
    void serializesToJsonCorrectly() throws Exception {
        OrderRequestDto dto = OrderRequestDto.builder()
                .userId("json-user")
                .totalPrice(BigDecimal.valueOf(39.99))
                .paymentMethod("CASH")
                .build();

        String json = objectMapper.writeValueAsString(dto);

        assertThat(json).contains("\"userId\":\"json-user\"");
        assertThat(json).contains("\"totalPrice\":39.99");
        assertThat(json).contains("\"paymentMethod\":\"CASH\"");
    }

    @Test
    void deserializesFromJsonCorrectly() throws Exception {
        String json = """
                {
                  "userId": "json-user",
                  "totalPrice": 59.90,
                  "paymentMethod": "CARD"
                }
                """;

        OrderRequestDto dto =
                objectMapper.readValue(json, OrderRequestDto.class);

        assertThat(dto.getUserId()).isEqualTo("json-user");
        assertThat(dto.getTotalPrice()).isEqualTo(BigDecimal.valueOf(59.90));
        assertThat(dto.getPaymentMethod()).isEqualTo("CARD");
    }
}
