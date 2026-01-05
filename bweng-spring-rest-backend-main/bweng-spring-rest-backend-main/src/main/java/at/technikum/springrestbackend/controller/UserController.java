package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.UserAdminUpdateRequestDto;
import at.technikum.springrestbackend.dto.UserRequestDto;
import at.technikum.springrestbackend.dto.UserResponseDto;
import at.technikum.springrestbackend.dto.UserUpdateRequestDto;
import at.technikum.springrestbackend.entity.User;
import at.technikum.springrestbackend.exception.UserNotFoundException;
import at.technikum.springrestbackend.mapper.UserMapper;
import at.technikum.springrestbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller exposing CRUD endpoints for users.
 */
//@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allows requests from frontend
public class UserController {

    private final UserService userService;

    /**
     * Returns all users.
     *
     * @return a list of user response DTOs
     */
    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Returns a single user identified by its id.
     *
     * @param id the UUID of the user to retrieve
     * @return the user as a response DTO
     */
    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable UUID id) {
        return userService.getUser(id);
    }
    /**
     * Returns the currently authenticated user's details.
     *
     * @param authentication the authentication object containing user details
     * @return the authenticated user as a response DTO
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public UserResponseDto getMe(Authentication authentication) {
        String username = authentication.getName();
        return  userService.findByUsernameOptional(username)
                .map(UserMapper::toResponseDto)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    /**
     * Creates a new user using the provided request data.
     *
     * @param dto the user creation request
     * @return a 201 Created response containing the created user
     */
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto dto) {
        UserResponseDto saved = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Updates an existing user identified by its id.
     * Only the allowed fields are updated in the underlying service.
     *
     * @param id  the UUID of the user to update
     * @param dto the updated values for the user
     * @return a 200 OK response containing the updated user
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UserUpdateRequestDto dto) {
        UserResponseDto updated = userService.updateUser(id, dto);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<UserResponseDto> updateAdminUser(
            @PathVariable UUID id,
            @Valid @RequestBody UserAdminUpdateRequestDto dto) {
        UserResponseDto updated = userService.updateAdminUser(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Deletes the user with the given id.
     *
     * @param id the UUID of the user to delete
     * @return a 204 No Content response if the deletion is successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
