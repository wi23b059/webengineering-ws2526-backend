package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.UserRequestDto;
import at.technikum.springrestbackend.dto.UserUpdateRequestDto;
import at.technikum.springrestbackend.entity.Role;
import at.technikum.springrestbackend.entity.Status;
import at.technikum.springrestbackend.entity.User;
import at.technikum.springrestbackend.exception.EmailAlreadyExistsException;
import at.technikum.springrestbackend.exception.UserNotFoundException;
import at.technikum.springrestbackend.exception.UsernameAlreadyExistsException;
import at.technikum.springrestbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void getAllUsers_shouldReturnAllUsers() {
        User user = new User();
        user.setId(UUID.randomUUID());

        when(userRepository.findAll()).thenReturn(List.of(user));

        var result = userService.getAllUsers();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(user.getId());
    }

    @Test
    void getUser_shouldReturnUser() {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setId(id);

        when(userRepository.findById(id))
                .thenReturn(Optional.of(user));

        var result = userService.getUser(id);

        assertThat(result.getId()).isEqualTo(id);
    }

    @Test
    void getUser_shouldThrowException_whenNotFound() {
        UUID id = UUID.randomUUID();

        when(userRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUser(id))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void createUser_shouldCreateUserSuccessfully() {
        UserRequestDto dto = new UserRequestDto();
        dto.setUsername("user1");
        dto.setEmail("test@test.com");
        dto.setPassword("secret");

        when(userRepository.findByEmail(dto.getEmail()))
                .thenReturn(Optional.empty());
        when(userRepository.findByUsername(dto.getUsername()))
                .thenReturn(Optional.empty());
        when(passwordEncoder.encode("secret"))
                .thenReturn("encoded");
        when(userRepository.save(any(User.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        var result = userService.createUser(dto);

        assertThat(result.getUsername()).isEqualTo("user1");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_shouldThrowException_whenEmailExists() {
        UserRequestDto dto = new UserRequestDto();
        dto.setEmail("test@test.com");

        when(userRepository.findByEmail(dto.getEmail()))
                .thenReturn(Optional.of(new User()));

        assertThatThrownBy(() -> userService.createUser(dto))
                .isInstanceOf(EmailAlreadyExistsException.class);
    }

    @Test
    void createUser_shouldThrowException_whenUsernameExists() {
        UserRequestDto dto = new UserRequestDto();
        dto.setEmail("test@test.com");
        dto.setUsername("user1");

        when(userRepository.findByEmail(dto.getEmail()))
                .thenReturn(Optional.empty());
        when(userRepository.findByUsername(dto.getUsername()))
                .thenReturn(Optional.of(new User()));

        assertThatThrownBy(() -> userService.createUser(dto))
                .isInstanceOf(UsernameAlreadyExistsException.class);
    }

    @Test
    void updateUser_shouldUpdateUser_withoutPasswordChange() {
        UUID id = UUID.randomUUID();
        User existing = new User();
        existing.setId(id);

        UserUpdateRequestDto dto = new UserUpdateRequestDto();
        dto.setFirstName("Max");

        when(userRepository.findById(id))
                .thenReturn(Optional.of(existing));
        when(userRepository.save(existing))
                .thenReturn(existing);

        var result = userService.updateUser(id, dto);

        assertThat(result.getId()).isEqualTo(id);
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    void updateUser_shouldUpdateUser_withPasswordChange() {
        UUID id = UUID.randomUUID();
        User existing = new User();
        existing.setId(id);

        UserUpdateRequestDto dto = new UserUpdateRequestDto();
        dto.setPassword("newpass");

        when(userRepository.findById(id))
                .thenReturn(Optional.of(existing));
        when(passwordEncoder.encode("newpass"))
                .thenReturn("encoded");
        when(userRepository.save(existing))
                .thenReturn(existing);

        userService.updateUser(id, dto);

        verify(passwordEncoder).encode("newpass");
    }

    @Test
    void updateUser_shouldThrowException_whenNotFound() {
        UUID id = UUID.randomUUID();

        when(userRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.updateUser(id, new UserUpdateRequestDto()))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void deleteUser_shouldDeleteUser() {
        UUID id = UUID.randomUUID();

        when(userRepository.existsById(id))
                .thenReturn(true);

        userService.deleteUser(id);

        verify(userRepository).deleteById(id);
    }

    @Test
    void deleteUser_shouldThrowException_whenNotFound() {
        UUID id = UUID.randomUUID();

        when(userRepository.existsById(id))
                .thenReturn(false);

        assertThatThrownBy(() -> userService.deleteUser(id))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void findByUsernameOptional_shouldReturnUser() {
        when(userRepository.findByUsername("test"))
                .thenReturn(Optional.of(new User()));

        var result = userService.findByUsernameOptional("test");

        assertThat(result).isPresent();
    }

    @Test
    void findByEmailOptional_shouldReturnUser() {
        when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(new User()));

        var result = userService.findByEmailOptional("test@test.com");

        assertThat(result).isPresent();
    }
}
