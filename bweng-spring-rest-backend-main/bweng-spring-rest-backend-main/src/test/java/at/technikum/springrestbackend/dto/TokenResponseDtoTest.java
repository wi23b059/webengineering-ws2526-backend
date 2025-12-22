package at.technikum.springrestbackend.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link TokenResponseDto}.
 */
class TokenResponseDtoTest {

    @Test
    void allArgsConstructorSetsTokenCorrectly() {
        TokenResponseDto dto = new TokenResponseDto("my-jwt-token");

        assertThat(dto.getToken()).isEqualTo("my-jwt-token");
    }

    @Test
    void noArgsConstructorAndSetterWorkCorrectly() {
        TokenResponseDto dto = new TokenResponseDto();
        dto.setToken("another-token");

        assertThat(dto.getToken()).isEqualTo("another-token");
    }
}
