package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.UserRequestDto;
import at.technikum.springrestbackend.dto.UserResponseDto;
import at.technikum.springrestbackend.entity.User;

/**
 * Mapper class responsible for converting between {@link User} entities
 * and their corresponding request and response DTOs.
 */
public class UserMapper {

    /**
     * Converts a validated request DTO into a new User entity.
     * <p>
     * - Does NOT set password (service should encode and set it).
     * - Does NOT set role/status (service should apply defaults).
     * - Does NOT set id or timestamps (managed by JPA/Hibernate).
     *
     * @param dto the incoming DTO from client request
     * @return a new User entity
     */
    public static User toEntity(UserRequestDto dto) {
        return User.builder()
                .salutation(dto.getSalutation())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .address(dto.getAddress())
                .zip(dto.getZip())
                .city(dto.getCity())
                .email(dto.getEmail())
                .username(dto.getUsername())
                .build();
    }

    /**
     * Converts a {@link User} entity into a {@link UserResponseDto}.
     * <p>
     * Includes all non-sensitive information, excluding password.
     * Used when returning user data to the frontend.
     *
     * @param user the user entity from the database
     * @return  UserResponseDto
     */
    public static UserResponseDto toResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .salutation(user.getSalutation())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .zip(user.getZip())
                .city(user.getCity())
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
