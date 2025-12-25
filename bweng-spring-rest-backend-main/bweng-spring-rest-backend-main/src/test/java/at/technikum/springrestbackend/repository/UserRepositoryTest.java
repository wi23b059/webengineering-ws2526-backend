package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.entity.Role;
import at.technikum.springrestbackend.entity.Salutation;
import at.technikum.springrestbackend.entity.Status;
import at.technikum.springrestbackend.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveAndFindByEmail_shouldReturnUser() {
        // given
        User user = User.builder()
                .salutation(Salutation.MR)
                .firstName("Max")
                .lastName("Mustermann")
                .countryCode("AT")
                .address("Musterstraße 1")
                .zip("1010")
                .city("Wien")
                .email("max@example.com")
                .username("max123")
                .password("Secret123")
                .role(Role.USER)
                .status(Status.ACTIVE)
                .build();

        userRepository.save(user);

        // when
        Optional<User> found = userRepository.findByEmail("max@example.com");

        // then
        assertTrue(found.isPresent());
        assertEquals("max123", found.get().getUsername());
    }

    @Test
    void saveAndFindByUsername_shouldReturnUser() {
        // given
        User user = User.builder()
                .salutation(Salutation.MS)
                .firstName("Anna")
                .lastName("Muster")
                .countryCode("DE")
                .address("Beispielweg 2")
                .zip("20095")
                .city("Hamburg")
                .email("anna@example.com")
                .username("anna123")
                .password("Passw0rd")
                .role(Role.USER)
                .status(Status.ACTIVE)
                .build();

        userRepository.save(user);

        // when
        Optional<User> found = userRepository.findByUsername("anna123");

        // then
        assertTrue(found.isPresent());
        assertEquals("anna@example.com", found.get().getEmail());
    }

    @Test
    void findByEmail_shouldReturnEmptyOptionalIfNotFound() {
        Optional<User> found = userRepository.findByEmail("nonexistent@example.com");
        assertTrue(found.isEmpty());
    }

    @Test
    void findByUsername_shouldReturnEmptyOptionalIfNotFound() {
        Optional<User> found = userRepository.findByUsername("doesnotexist");
        assertTrue(found.isEmpty());
    }
}
