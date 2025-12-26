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

        when(userService.findByUsernameOptional("bob"))
                .thenReturn(Optional.of(user));
        when(userService.findByEmailOptional("bob"))
                .thenReturn(Optional.empty());

        UserDetails result = customUserDetailsService.loadUserByUsername("bob");

        assertNotNull(result);
        assertEquals("bob", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
        assertTrue(result.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));

        verify(userService).findByUsernameOptional("bob");
        verify(userService).findByEmailOptional("bob");
    }

    @Test
    void loadUserByUsername_findsUserByEmail() {
        UUID userId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .username("alice")
                .password("encodedPassword")
                .role(Role.ADMIN)
                .build();

        when(userService.findByUsernameOptional("alice@example.com"))
                .thenReturn(Optional.empty());
        when(userService.findByEmailOptional("alice@example.com"))
                .thenReturn(Optional.of(user));

        UserDetails result = customUserDetailsService.loadUserByUsername("alice@example.com");

        assertNotNull(result);
        assertEquals("alice", result.getUsername());
        assertTrue(result.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void loadUserByUsername_throwsException_whenUserNotFound() {
        when(userService.findByUsernameOptional("unknown"))
                .thenReturn(Optional.empty());
        when(userService.findByEmailOptional("unknown"))
                .thenReturn(Optional.empty());

        UsernameNotFoundException ex = assertThrows(
                UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername("unknown")
        );

        assertTrue(ex.getMessage().contains("User not found"));
    }
}
