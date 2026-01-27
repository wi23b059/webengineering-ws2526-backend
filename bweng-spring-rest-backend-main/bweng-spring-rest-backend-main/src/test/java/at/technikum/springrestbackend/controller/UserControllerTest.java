package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.*;
import at.technikum.springrestbackend.entity.Role;
import at.technikum.springrestbackend.entity.User;
import at.technikum.springrestbackend.exception.UserNotFoundException;
import at.technikum.springrestbackend.mapper.UserMapper;
import at.technikum.springrestbackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link UserController}.
 */

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserController userController;

    private UUID userId;
    private UserResponseDto sampleUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userId = UUID.randomUUID();
        sampleUser = UserResponseDto.builder()
                .id(userId)
                .username("testuser")
                .email("test@test.com")
                .role(Role.USER)
                .build();
    }

    @Test
    void testGetAllUsers() {
        when(userService.getAllUsers()).thenReturn(List.of(sampleUser));

        List<UserResponseDto> result = userController.getAllUsers();

        assertEquals(1, result.size());
        verify(userService).getAllUsers();
    }

    @Test
    void testGetUserById() {
        when(userService.getUser(userId)).thenReturn(sampleUser);

        UserResponseDto result = userController.getUserById(userId);

        assertEquals(sampleUser.getId(), result.getId());
        verify(userService).getUser(userId);
    }

    @Test
    void testGetMe_UserExists() {
        when(authentication.getName()).thenReturn("testuser");
        when(userService.findByUsernameOptional("testuser")).thenReturn(Optional.of(new User()));
        // Use mapper mock if necessary
        UserResponseDto mapped = sampleUser;
        try (MockedStatic<UserMapper> mapperMock = mockStatic(UserMapper.class)) {
            mapperMock.when(() -> UserMapper.toResponseDto(any(User.class))).thenReturn(mapped);

            UserResponseDto result = userController.getMe(authentication);

            assertEquals(sampleUser.getUsername(), result.getUsername());
        }

        verify(userService).findByUsernameOptional("testuser");
    }

    @Test
    void testGetMe_UserNotFound() {
        when(authentication.getName()).thenReturn("notfound");
        when(userService.findByUsernameOptional("notfound")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userController.getMe(authentication));
    }

    @Test
    void testCreateUser() {
        UserRequestDto requestDto = new UserRequestDto();
        when(userService.createUser(requestDto)).thenReturn(sampleUser);

        ResponseEntity<UserResponseDto> response = userController.createUser(requestDto);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(sampleUser.getId(), response.getBody().getId());
        verify(userService).createUser(requestDto);
    }

    @Test
    void testUpdateUser() {
        UserUpdateRequestDto requestDto = new UserUpdateRequestDto();
        when(userService.updateUser(userId, requestDto)).thenReturn(sampleUser);

        ResponseEntity<UserResponseDto> response = userController.updateUser(userId, requestDto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(sampleUser.getId(), response.getBody().getId());
        verify(userService).updateUser(userId, requestDto);
    }

    @Test
    void testUpdateAdminUser() {
        UserAdminUpdateRequestDto requestDto = new UserAdminUpdateRequestDto();
        when(userService.updateAdminUser(userId, requestDto)).thenReturn(sampleUser);

        ResponseEntity<UserResponseDto> response = userController.updateAdminUser(userId, requestDto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(sampleUser.getId(), response.getBody().getId());
        verify(userService).updateAdminUser(userId, requestDto);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userService).deleteUser(userId);

        ResponseEntity<Void> response = userController.deleteUser(userId);

        assertEquals(204, response.getStatusCode().value());
        verify(userService).deleteUser(userId);
    }

    @Test
    void testChangePassword() {
        ChangePasswordRequestDto dto = new ChangePasswordRequestDto();
        dto.setCurrentPassword("oldpass");
        dto.setNewPassword("newpass");
        doNothing().when(userService).changePassword(userId, "oldpass", "newpass");

        ResponseEntity<Void> response = userController.changePassword(userId, dto);

        assertEquals(204, response.getStatusCode().value());
        verify(userService).changePassword(userId, "oldpass", "newpass");
    }
}
