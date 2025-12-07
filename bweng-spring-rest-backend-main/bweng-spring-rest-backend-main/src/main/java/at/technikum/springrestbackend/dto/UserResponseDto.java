package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.entity.Role;
import at.technikum.springrestbackend.entity.Salutation;
import at.technikum.springrestbackend.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

/**
 * Data Transfer Object used to return user information in API responses.
 * Does not include sensitive fields such as password.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {

    private UUID id;

    private Salutation salutation;
    private String firstName;
    private String lastName;

    private String countryCode;
    private String address;
    private String zip;
    private String city;

    private String email;
    private String username;
    private String profilePicturePath;

    private Role role;
    private Status status;

    private Instant createdAt;
    private Instant updatedAt;
}
