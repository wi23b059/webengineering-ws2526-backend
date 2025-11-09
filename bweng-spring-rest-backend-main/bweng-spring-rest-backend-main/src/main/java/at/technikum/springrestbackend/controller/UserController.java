package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.UserRequestDto;
import at.technikum.springrestbackend.dto.UserResponseDto;
import at.technikum.springrestbackend.entity.User;
import at.technikum.springrestbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // erlaubt Aufrufe vom Frontend
public class UserController {

    private final UserService userService;

    // GET /api/users
    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    // GET /api/users/{id}
    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable UUID id) {
        return userService.getUser(id);
    }

    // POST /api/users
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto dto) {
        UserResponseDto saved = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // DELETE /api/users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
