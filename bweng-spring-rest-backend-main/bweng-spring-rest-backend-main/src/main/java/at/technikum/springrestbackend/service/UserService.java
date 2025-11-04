package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.UserDto;
import at.technikum.springrestbackend.entity.User;
import at.technikum.springrestbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> {
                    UserDto dto = new UserDto();
                    dto.id = user.getId();
                    dto.salutation = user.getSalutation();
                    dto.first_name = user.getFirstName();
                    dto.last_name = user.getLastName();
                    dto.email = user.getEmail();
                    dto.username = user.getUsername();
                    return dto;
                }).toList();
    }

    public UserDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDto dto = new UserDto();
        dto.id = user.getId();
        dto.salutation = user.getSalutation();
        dto.first_name = user.getFirstName();
        dto.last_name = user.getLastName();
        dto.email = user.getEmail();
        dto.username = user.getUsername();

        return dto;
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
