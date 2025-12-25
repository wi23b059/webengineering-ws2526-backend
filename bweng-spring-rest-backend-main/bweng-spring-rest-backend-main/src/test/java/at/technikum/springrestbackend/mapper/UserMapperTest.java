package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.UserRequestDto;
import at.technikum.springrestbackend.dto.UserResponseDto;
import at.technikum.springrestbackend.entity.Role;
import at.technikum.springrestbackend.entity.Salutation;
import at.technikum.springrestbackend.entity.Status;
import at.technikum.springrestbackend.entity.User;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void toEntity_shouldMapUserRequestDtoToUserEntity() {
        // given
        UserRequestDto dto = new UserRequestDto(
                Salutation.MR,
                "Max",
                "Mustermann",
                "AT",
                "Musterstraße 1",
                "Wien",
                "1010",
                "max@example.com",
                "maxuser",
                "Password1",
                "/img/max.png"
        );

        // when
        User user = UserMapper.toEntity(dto);

        // then
        assertNotNull(user);
        assertEquals(Salutation.MR, user.getSalutation());
        assertEquals("Max", user.getFirstName());
        assertEquals("Mustermann", user.getLastName());
        assertEquals("AT", user.getCountryCode());
        assertEquals("Musterstraße 1", user.getAddress());
        assertEquals("Wien", user.getCity());
        assertEquals("1010", user.getZip());
        assertEquals("max@example.com", user.getEmail());
        assertEquals("maxuser", user.getUsername());
        assertEquals("/img/max.png", user.getProfilePicturePath());

        // intentionally NOT set by mapper
        assertNull(user.getId());
        assertNull(user.getPassword());
        assertNull(user.getRole());
        assertNull(user.getStatus());
        assertNull(user.getCreatedAt());
        assertNull(user.getUpdatedAt());
    }

    @Test
    void toResponseDto_shouldMapUserEntityToUserResponseDto() {
        // given
        UUID id = UUID.randomUUID();
        Instant now = Instant.now();

        User user = User.builder()
                .id(id)
                .salutation(Salutation.MS)
                .firstName("Anna")
                .lastName("Musterfrau")
                .countryCode("DE")
                .address("Hauptstraße 5")
                .zip("80331")
                .city("München")
                .email("anna@example.com")
                .username("annauser")
                .profilePicturePath("/img/anna.png")
                .role(Role.USER)
                .status(Status.ACTIVE)
                .createdAt(now)
                .updatedAt(now)
                .build();

        // when
        UserResponseDto responseDto = UserMapper.toResponseDto(user);

        // then
        assertNotNull(responseDto);
        assertEquals(id, responseDto.getId());
        assertEquals(Salutation.MS, responseDto.getSalutation());
        assertEquals("Anna", responseDto.getFirstName());
        assertEquals("Musterfrau", responseDto.getLastName());
        assertEquals("DE", responseDto.getCountryCode());
        assertEquals("Hauptstraße 5", responseDto.getAddress());
        assertEquals("80331", responseDto.getZip());
        assertEquals("München", responseDto.getCity());
        assertEquals("anna@example.com", responseDto.getEmail());
        assertEquals("annauser", responseDto.getUsername());
        assertEquals("/img/anna.png", responseDto.getProfilePicturePath());
        assertEquals(Role.USER, responseDto.getRole());
        assertEquals(Status.ACTIVE, responseDto.getStatus());
        assertEquals(now, responseDto.getCreatedAt());
        assertEquals(now, responseDto.getUpdatedAt());
    }
}
