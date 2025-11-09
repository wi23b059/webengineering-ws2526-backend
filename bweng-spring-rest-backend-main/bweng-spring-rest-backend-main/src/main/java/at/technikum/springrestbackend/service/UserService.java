package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.UserRequestDto;
import at.technikum.springrestbackend.dto.UserResponseDto;
import at.technikum.springrestbackend.entity.Role;
import at.technikum.springrestbackend.entity.Status;
import at.technikum.springrestbackend.entity.User;
import at.technikum.springrestbackend.mapper.UserMapper;
import at.technikum.springrestbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponseDto)
                .toList();
    }

    public UserResponseDto getUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toResponseDto(user);
    }

    public UserResponseDto createUser(UserRequestDto dto) {
        User user = UserMapper.toEntity(dto);
        user.setRole(Role.USER);        // Standard-Rolle
        user.setStatus(Status.ACTIVE);  // Standard-Status
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        User saved = userRepository.save(user);
        return UserMapper.toResponseDto(saved);
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
