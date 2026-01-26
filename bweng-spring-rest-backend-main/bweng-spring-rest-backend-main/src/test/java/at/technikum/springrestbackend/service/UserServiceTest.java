package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.*;
import at.technikum.springrestbackend.entity.Role;
import at.technikum.springrestbackend.entity.Status;
import at.technikum.springrestbackend.entity.User;
import at.technikum.springrestbackend.exception.EmailAlreadyExistsException;
import at.technikum.springrestbackend.exception.UserNotFoundException;
import at.technikum.springrestbackend.exception.UsernameAlreadyExistsException;
import at.technikum.springrestbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        User user = User.builder().id(UUID.randomUUID()).username("testuser").build();
        when(userRepository.findAll()).thenReturn(List.of(user));

        var result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals("testuser", result.getFirst().getUsername());
    }

    @Test
    void getAllUsers_emptyList_shouldReturnEmptyList() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        var result = userService.getAllUsers();
        assertTrue(result.isEmpty());
    }

    @Test
    void getUser_existingUser_shouldReturnUser() {
        UUID id = UUID.randomUUID();
        User user = User.builder().id(id).username("testuser").build();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        var result = userService.getUser(id);

        assertEquals("testuser", result.getUsername());
        assertEquals(id, result.getId());
    }

    @Test
    void getUser_nonExistingUser_shouldThrowException() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUser(id));
    }

    @Test
    void createUser_uniqueEmailAndUsername_shouldReturnUser() {
        UserRequestDto dto = new UserRequestDto();
        dto.setEmail("test@example.com");
        dto.setUsername("testuser");
        dto.setPassword("password");

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(dto.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");

        User savedUser = User.builder()
                .id(UUID.randomUUID())
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password("encodedPassword")
                .role(Role.USER)
                .status(Status.ACTIVE)
                .build();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        var result = userService.createUser(dto);

        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void createUser_existingEmail_shouldThrowException() {
        UserRequestDto dto = new UserRequestDto();
        dto.setEmail("exists@example.com");
        dto.setUsername("testuser");

        when(userRepository.findByEmail(dto.getEmail()))
                .thenReturn(Optional.of(new User()));

        assertThrows(EmailAlreadyExistsException.class, () -> userService.createUser(dto));
    }

    @Test
    void createUser_existingUsername_shouldThrowException() {
        UserRequestDto dto = new UserRequestDto();
        dto.setEmail("test@example.com");
        dto.setUsername("exists");

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(dto.getUsername()))
                .thenReturn(Optional.of(new User()));

        assertThrows(UsernameAlreadyExistsException.class, () -> userService.createUser(dto));
    }

    @Test
    void updateUser_existingUser_withPassword_shouldUpdate() {
        UUID id = UUID.randomUUID();
        UserUpdateRequestDto dto = new UserUpdateRequestDto();
        dto.setFirstName("NewFirst");
        dto.setPassword("newPass");

        User existing = User.builder().id(id).password("oldPass").build();
        when(userRepository.findById(id)).thenReturn(Optional.of(existing));
        when(passwordEncoder.encode("newPass")).thenReturn("encodedNewPass");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = userService.updateUser(id, dto);

        assertEquals("NewFirst", result.getFirstName());
        assertEquals("encodedNewPass", existing.getPassword());
    }

    @Test
    void updateUser_existingUser_withoutPassword_shouldUpdateOtherFields() {
        UUID id = UUID.randomUUID();
        UserUpdateRequestDto dto = new UserUpdateRequestDto();
        dto.setFirstName("NoPass");

        User existing = User.builder().id(id).password("oldPass").build();
        when(userRepository.findById(id)).thenReturn(Optional.of(existing));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = userService.updateUser(id, dto);

        assertEquals("NoPass", result.getFirstName());
        assertEquals("oldPass", existing.getPassword()); // password unchanged
    }

    @Test
    void updateAdminUser_existingUser_withPassword_shouldUpdateAllFields() {
        UUID id = UUID.randomUUID();
        UserAdminUpdateRequestDto dto = new UserAdminUpdateRequestDto();
        dto.setUsername("adminuser");
        dto.setEmail("admin@example.com");
        dto.setRole(Role.ADMIN);
        dto.setStatus(Status.ACTIVE);
        dto.setPassword("newAdminPass");

        User existing = User.builder().id(id).password("oldPass").build();
        when(userRepository.findById(id)).thenReturn(Optional.of(existing));
        when(passwordEncoder.encode("newAdminPass")).thenReturn("encodedNewAdmin");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = userService.updateAdminUser(id, dto);

        assertEquals("adminuser", result.getUsername());
        assertEquals(Role.ADMIN, result.getRole());
        assertEquals("encodedNewAdmin", existing.getPassword());
    }

    @Test
    void updateAdminUser_existingUser_withoutPassword_shouldUpdateOtherFields() {
        UUID id = UUID.randomUUID();
        UserAdminUpdateRequestDto dto = new UserAdminUpdateRequestDto();
        dto.setUsername("adminuserNoPass");
        dto.setEmail("admin@example.com");
        dto.setRole(Role.USER);
        dto.setStatus(Status.ACTIVE);
        dto.setPassword(null); // branch: password null

        User existing = User.builder().id(id).password("oldPass").build();
        when(userRepository.findById(id)).thenReturn(Optional.of(existing));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = userService.updateAdminUser(id, dto);

        assertEquals("adminuserNoPass", result.getUsername());
        assertEquals("oldPass", existing.getPassword());
    }

    @Test
    void deleteUser_existingUser_shouldCallDelete() {
        UUID id = UUID.randomUUID();
        when(userRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> userService.deleteUser(id));
        verify(userRepository).deleteById(id);
    }

    @Test
    void deleteUser_nonExistingUser_shouldThrowException() {
        UUID id = UUID.randomUUID();
        when(userRepository.existsById(id)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(id));
    }

    @Test
    void changePassword_correctCurrentPassword_shouldUpdate() {
        UUID id = UUID.randomUUID();
        User user = User.builder().id(id).password("encodedOld").build();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("oldPass", "encodedOld")).thenReturn(true);
        when(passwordEncoder.encode("newPass")).thenReturn("encodedNew");

        userService.changePassword(id, "oldPass", "newPass");

        assertEquals("encodedNew", user.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    void changePassword_wrongCurrentPassword_shouldThrowException() {
        UUID id = UUID.randomUUID();
        User user = User.builder().id(id).password("encodedOld").build();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "encodedOld")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> userService.changePassword(id, "wrong", "newPass"));
    }

    @Test
    void findByUsernameOptional_shouldReturnUserOptional() {
        User user = User.builder().username("test").build();
        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        var result = userService.findByUsernameOptional("test");
        assertTrue(result.isPresent());
    }

    @Test
    void findByEmailOptional_shouldReturnUserOptional() {
        User user = User.builder().email("test@example.com").build();
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        var result = userService.findByEmailOptional("test@example.com");
        assertTrue(result.isPresent());
    }
}
