package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.UserAdminUpdateRequestDto;
import at.technikum.springrestbackend.dto.UserRequestDto;
import at.technikum.springrestbackend.dto.UserResponseDto;
import at.technikum.springrestbackend.dto.UserUpdateRequestDto;
import at.technikum.springrestbackend.entity.Role;
import at.technikum.springrestbackend.entity.Status;
import at.technikum.springrestbackend.entity.User;
import at.technikum.springrestbackend.exception.EmailAlreadyExistsException;
import at.technikum.springrestbackend.exception.UserNotFoundException;
import at.technikum.springrestbackend.exception.UsernameAlreadyExistsException;
import at.technikum.springrestbackend.mapper.UserMapper;
import at.technikum.springrestbackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
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
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
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
    //@PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @Transactional
    public UserResponseDto updateUser(UUID id, UserUpdateRequestDto dto) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // Update fields
        existing.setSalutation(dto.getSalutation());
        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setCountryCode(dto.getCountryCode());
        existing.setAddress(dto.getAddress());
        existing.setZip(dto.getZip());
        existing.setCity(dto.getCity());
        existing.setProfilePicturePath(dto.getProfilePicturePath());

        // Optional: Update password, if dto.getPassword() != null
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        User updated = userRepository.save(existing);
        return UserMapper.toResponseDto(updated);
    }

    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @Transactional
    public UserResponseDto updateAdminUser(UUID id, UserAdminUpdateRequestDto dto) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // Update fields
        existing.setSalutation(dto.getSalutation());
        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setCountryCode(dto.getCountryCode());
        existing.setAddress(dto.getAddress());
        existing.setZip(dto.getZip());
        existing.setCity(dto.getCity());
        existing.setEmail(dto.getEmail());
        existing.setUsername(dto.getUsername());
        existing.setRole(dto.getRole());
        existing.setStatus(dto.getStatus());
        existing.setProfilePicturePath(dto.getProfilePicturePath());

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
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @Transactional
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    /** Suche User nach Username */
    //@PreAuthorize("hasRole('ADMIN')")
    public Optional<User> findByUsernameOptional(String username) {
        return userRepository.findByUsername(username);
    }

    /** Suche User nach Email */
    //@PreAuthorize("hasRole('ADMIN')")
    public Optional<User> findByEmailOptional(String email) {
        return userRepository.findByEmail(email);
    }

    @PreAuthorize("#id == authentication.principal.id")
    @Transactional
    public void changePassword(UUID id, String currentPassword, String newPassword) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // Aktuelles Passwort prüfen
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Aktuelles Passwort ist falsch");
        }

        // Neues Passwort setzen
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
