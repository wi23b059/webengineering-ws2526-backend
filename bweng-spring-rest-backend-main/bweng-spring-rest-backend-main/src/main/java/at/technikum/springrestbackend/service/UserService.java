package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.UserRequestDto;
import at.technikum.springrestbackend.dto.UserResponseDto;
import at.technikum.springrestbackend.entity.Role;
import at.technikum.springrestbackend.entity.Status;
import at.technikum.springrestbackend.entity.User;
import at.technikum.springrestbackend.exception.EmailAlreadyExistsException;
import at.technikum.springrestbackend.exception.UserNotFoundException;
import at.technikum.springrestbackend.exception.UsernameAlreadyExistsException;
import at.technikum.springrestbackend.mapper.CategoryMapper;
import at.technikum.springrestbackend.mapper.UserMapper;
import at.technikum.springrestbackend.repository.CategoryRepository;
import at.technikum.springrestbackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

/**
 * Service providing CRUD operations for {@code User} entities.
 * Uses {@link UserRepository} and {@link UserMapper} to access and expose data.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Returns all users as response DTOs.
     */
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponseDto)
                .toList();
    }

    /**
     * Returns a single user by id.
     *
     * @param id the UUID of the user
     * @return the user as a response DTO
     * @throws UserNotFoundException if the user does not exist
     */
    public UserResponseDto getUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return UserMapper.toResponseDto(user);
    }

    /**
     * Creates a new user with encoded password, default role/status, and validated uniqueness.
     *
     * @param dto the user creation request
     * @return the created user as a response DTO
     * @throws EmailAlreadyExistsException    if the provided email is already registered
     * @throws UsernameAlreadyExistsException if the provided username is already taken
     */
    @Transactional
    public UserResponseDto createUser(UserRequestDto dto) {

        // 1. Check email uniqueness
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException(dto.getEmail());
        }

        // 2. Check username uniqueness
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException(dto.getUsername());
        }

        User user = UserMapper.toEntity(dto);
        user.setRole(Role.USER);        // Standard-Role
        user.setStatus(Status.ACTIVE);  // Standard-Status
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        User saved = userRepository.save(user);
        return UserMapper.toResponseDto(saved);
    }

    /**
     * Updates an existing user (except username and role).
     *
     * @param id  the UUID of the user to update
     * @param dto the updated values
     * @return the updated user as a response DTO
     * @throws UserNotFoundException if the user does not exist
     */
    @Transactional
    public UserResponseDto updateUser(UUID id, UserRequestDto dto) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // Update fields
        existing.setSalutation(dto.getSalutation());
        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setAddress(dto.getAddress());
        existing.setZip(dto.getZip());
        existing.setCity(dto.getCity());

        // Optional: Update password, if dto.getPassword() != null
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        User updated = userRepository.save(existing);
        return UserMapper.toResponseDto(updated);
    }

    /**
     * Deletes a user by id.
     *
     * @param id the UUID of the user to delete
     * @throws UserNotFoundException if the user does not exist
     */
    @Transactional
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Authenticates a user using email and password.
     *
     * @param email the email of the user attempting to log in
     * @param rawPassword the plaintext password provided by the user
     * @return the authenticated user as a response DTO
     * @throws UserNotFoundException if no user with the given email exists
     * @throws BadCredentialsException if the password is incorrect
     */
    public UserResponseDto login(String email, String rawPassword) {

        // 1) Find user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        // 2) Compare passwords
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }

        // 3) Convert to response DTO
        return UserMapper.toResponseDto(user);
    }
}
