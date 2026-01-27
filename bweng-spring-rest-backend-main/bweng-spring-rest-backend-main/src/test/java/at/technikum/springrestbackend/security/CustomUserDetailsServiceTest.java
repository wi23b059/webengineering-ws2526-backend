package at.technikum.springrestbackend.security;

import at.technikum.springrestbackend.entity.Role;
import at.technikum.springrestbackend.entity.User;
import at.technikum.springrestbackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link CustomUserDetailsService}.
 */

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void loadUserByUsername_findsUserByUsername() {
        UUID userId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .username("bob")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        // Mock: User wird per Username gefunden
        when(userService.findByUsernameOptional("bob"))
                .thenReturn(Optional.of(user));

        // Act
        UserDetails result = customUserDetailsService.loadUserByUsername("bob");

        // Assert
        assertNotNull(result);
        assertEquals("bob", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
        assertTrue(result.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));

        // Verify: nur findByUsernameOptional wird aufgerufen
        verify(userService).findByUsernameOptional("bob");
        verifyNoMoreInteractions(userService);
    }

    @Test
    void loadUserByUsername_throwsException_whenUserNotFound() {
        // Mock: kein User vorhanden
        when(userService.findByUsernameOptional("unknown"))
                .thenReturn(Optional.empty());

        // Act & Assert
        UsernameNotFoundException ex = assertThrows(
                UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername("unknown")
        );

        assertTrue(ex.getMessage().contains("User not found"));

        verify(userService).findByUsernameOptional("unknown");
        verifyNoMoreInteractions(userService);
    }
}
