package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.entity.Role;
import at.technikum.springrestbackend.entity.Salutation;
import at.technikum.springrestbackend.entity.Status;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link UserResponseDto}.
 */
class UserResponseDtoTest {

    @Test
    void allArgsConstructorSetsFieldsCorrectly() {
        UUID id = UUID.randomUUID();
        Instant now = Instant.now();

        UserResponseDto dto = new UserResponseDto(
                id,
                Salutation.MR,
                "John",
                "Doe",
                "AT",
                "Street 1",
                "1010",
                "Vienna",
                "john.doe@example.com",
                "johndoe",
                "/img/john.png",
                Role.USER,
                Status.ACTIVE,
                now,
                now
        );

        assertThat(dto.getId()).isEqualTo(id);
        assertThat(dto.getSalutation()).isEqualTo(Salutation.MR);
        assertThat(dto.getFirstName()).isEqualTo("John");
        assertThat(dto.getLastName()).isEqualTo("Doe");
        assertThat(dto.getCountryCode()).isEqualTo("AT");
        assertThat(dto.getAddress()).isEqualTo("Street 1");
        assertThat(dto.getZip()).isEqualTo("1010");
        assertThat(dto.getCity()).isEqualTo("Vienna");
        assertThat(dto.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(dto.getUsername()).isEqualTo("johndoe");
        assertThat(dto.getProfilePicturePath()).isEqualTo("/img/john.png");
        assertThat(dto.getRole()).isEqualTo(Role.USER);
        assertThat(dto.getStatus()).isEqualTo(Status.ACTIVE);
        assertThat(dto.getCreatedAt()).isEqualTo(now);
        assertThat(dto.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void builderCreatesCorrectObject() {
        UUID id = UUID.randomUUID();
        Instant now = Instant.now();

        UserResponseDto dto = UserResponseDto.builder()
                .id(id)
                .salutation(Salutation.MR)
                .firstName("Alice")
                .lastName("Smith")
                .countryCode("DE")
                .address("Main Street")
                .zip("12345")
                .city("Berlin")
                .email("alice@example.com")
                .username("alice123")
                .profilePicturePath("/img/alice.png")
                .role(Role.ADMIN)
                .status(Status.ACTIVE)
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertThat(dto.getId()).isEqualTo(id);
        assertThat(dto.getSalutation()).isEqualTo(Salutation.MR);
        assertThat(dto.getFirstName()).isEqualTo("Alice");
        assertThat(dto.getLastName()).isEqualTo("Smith");
        assertThat(dto.getCountryCode()).isEqualTo("DE");
        assertThat(dto.getAddress()).isEqualTo("Main Street");
        assertThat(dto.getZip()).isEqualTo("12345");
        assertThat(dto.getCity()).isEqualTo("Berlin");
        assertThat(dto.getEmail()).isEqualTo("alice@example.com");
        assertThat(dto.getUsername()).isEqualTo("alice123");
        assertThat(dto.getProfilePicturePath()).isEqualTo("/img/alice.png");
        assertThat(dto.getRole()).isEqualTo(Role.ADMIN);
        assertThat(dto.getStatus()).isEqualTo(Status.ACTIVE);
        assertThat(dto.getCreatedAt()).isEqualTo(now);
        assertThat(dto.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void noArgsConstructorAndSettersWorkCorrectly() {
        Instant now = Instant.now();
        UUID id = UUID.randomUUID();

        UserResponseDto dto = UserResponseDto.builder()
                .id(id)
                .salutation(Salutation.MR)
                .firstName("Bob")
                .lastName("Brown")
                .countryCode("CH")
                .address("Allee 10")
                .zip("8000")
                .city("Zurich")
                .email("bob@example.com")
                .username("bob123")
                .profilePicturePath("/img/bob.png")
                .role(Role.USER)
                .status(Status.INACTIVE)
                .createdAt(now)
                .updatedAt(now)
                .build();


        assertThat(dto.getId()).isEqualTo(id);
        assertThat(dto.getSalutation()).isEqualTo(Salutation.MR);
        assertThat(dto.getFirstName()).isEqualTo("Bob");
        assertThat(dto.getLastName()).isEqualTo("Brown");
        assertThat(dto.getCountryCode()).isEqualTo("CH");
        assertThat(dto.getAddress()).isEqualTo("Allee 10");
        assertThat(dto.getZip()).isEqualTo("8000");
        assertThat(dto.getCity()).isEqualTo("Zurich");
        assertThat(dto.getEmail()).isEqualTo("bob@example.com");
        assertThat(dto.getUsername()).isEqualTo("bob123");
        assertThat(dto.getProfilePicturePath()).isEqualTo("/img/bob.png");
        assertThat(dto.getRole()).isEqualTo(Role.USER);
        assertThat(dto.getStatus()).isEqualTo(Status.INACTIVE);
        assertThat(dto.getCreatedAt()).isEqualTo(now);
        assertThat(dto.getUpdatedAt()).isEqualTo(now);
    }
}
