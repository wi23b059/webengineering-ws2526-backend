package at.technikum.springrestbackend.dto;

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
 * Unit tests for {@link ProductRequestDto}.
 */
class ProductRequestDtoTest {

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
        ProductRequestDto dto = ProductRequestDto.builder()
                .name("Test Product")
                .description("This is a test product")
                .price(BigDecimal.valueOf(49.99))
                .rating("5 stars")
                .imagePath("/images/test.png")
                .categoryId(1)
                .build();

        Set<ConstraintViolation<ProductRequestDto>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void nullOrBlankFieldsFailValidation() {
        ProductRequestDto dto = ProductRequestDto.builder()
                .name("")   // Blank
                .description(null) // Null
                .price(BigDecimal.valueOf(-1)) // Invalid
                .categoryId(null) // Null
                .build();

        Set<ConstraintViolation<ProductRequestDto>> violations = validator.validate(dto);

        assertThat(violations).hasSizeGreaterThanOrEqualTo(4);
    }

    // -------------------------------------------------------------------------
    // Lombok
    // -------------------------------------------------------------------------

    @Test
    void allArgsConstructorCreatesCorrectObject() {
        ProductRequestDto dto = new ProductRequestDto(
                "Product A",
                "Desc A",
                BigDecimal.valueOf(10.50),
                "4 stars",
                "/img/a.png",
                2
        );

        assertThat(dto.getName()).isEqualTo("Product A");
        assertThat(dto.getDescription()).isEqualTo("Desc A");
        assertThat(dto.getPrice()).isEqualTo(BigDecimal.valueOf(10.50));
        assertThat(dto.getRating()).isEqualTo("4 stars");
        assertThat(dto.getImagePath()).isEqualTo("/img/a.png");
        assertThat(dto.getCategoryId()).isEqualTo(2);
    }

    @Test
    void builderCreatesCorrectObject() {
        ProductRequestDto dto = ProductRequestDto.builder()
                .name("Builder Product")
                .description("Builder Desc")
                .price(BigDecimal.valueOf(20.75))
                .rating("5 stars")
                .imagePath("/img/b.png")
                .categoryId(3)
                .build();

        assertThat(dto.getName()).isEqualTo("Builder Product");
        assertThat(dto.getDescription()).isEqualTo("Builder Desc");
        assertThat(dto.getPrice()).isEqualTo(BigDecimal.valueOf(20.75));
        assertThat(dto.getRating()).isEqualTo("5 stars");
        assertThat(dto.getImagePath()).isEqualTo("/img/b.png");
        assertThat(dto.getCategoryId()).isEqualTo(3);
    }
}
