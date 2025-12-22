package at.technikum.springrestbackend.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void shouldCreateUserWithAllFieldsUsingBuilder() {
        UUID id = UUID.randomUUID();
        Instant now = Instant.now();

        User user = User.builder()
                .id(id)
                .salutation(Salutation.MR)
                .firstName("Max")
                .lastName("Mustermann")
                .countryCode("AT")
                .address("Hauptstraße 1")
                .zip("1010")
                .city("Wien")
                .email("max@example.com")
                .username("max123")
                .password("Secret123")
                .profilePicturePath("/img/max.png")
                .role(Role.USER)
                .status(Status.ACTIVE)
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertEquals(id, user.getId());
        assertEquals(Salutation.MR, user.getSalutation());
        assertEquals("Max", user.getFirstName());
        assertEquals("Mustermann", user.getLastName());
        assertEquals("AT", user.getCountryCode());
        assertEquals("Hauptstraße 1", user.getAddress());
        assertEquals("1010", user.getZip());
        assertEquals("Wien", user.getCity());
        assertEquals("max@example.com", user.getEmail());
        assertEquals("max123", user.getUsername());
        assertEquals("Secret123", user.getPassword());
        assertEquals("/img/max.png", user.getProfilePicturePath());
        assertEquals(Role.USER, user.getRole());
        assertEquals(Status.ACTIVE, user.getStatus());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
    }

    @Test
    void shouldCreateUserWithUsernameAndPasswordConstructor() {
        User user = new User("john_doe", "Password123");

        assertEquals("john_doe", user.getUsername());
        assertEquals("Password123", user.getPassword());
    }

    @Test
    void passwordShouldBeIgnoredInJsonSerialization() throws Exception {
        User user = User.builder()
                .username("alice")
                .password("SecretPassword")
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);

        assertFalse(json.contains("password"));
        assertTrue(json.contains("alice"));
    }

    @Test
    void shouldAllowNullOptionalFields() {
        User user = User.builder()
                .username("optionalUser")
                .password("Password123")
                .role(Role.USER)
                .status(Status.INACTIVE)
                .build();

        assertNull(user.getProfilePicturePath());
        assertNull(user.getSalutation());
        assertNull(user.getEmail());
    }

    @Test
    void enumsShouldBeStoredAsStrings() {
        User user = User.builder()
                .role(Role.ADMIN)
                .status(Status.BANNED)
                .build();

        assertEquals("ADMIN", user.getRole().name());
        assertEquals("BANNED", user.getStatus().name());
    }
}
