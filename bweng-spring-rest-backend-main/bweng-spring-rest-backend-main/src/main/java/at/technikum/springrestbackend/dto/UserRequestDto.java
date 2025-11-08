package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.entity.Salutation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Represents the data required to create or update a user.
 * <p>
 * Validates user input from API requests and ensures proper structure before it
 * reaches the service or persistence layer.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    @NotNull
    private Salutation salutation;

    @NotBlank(message = "First name is required")
    @Size(max = 60, message = "First name must be at most 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 60, message = "Last name cannot exceed 60 characters")
    private String lastName;

    @NotBlank(message = "Address is required")
    @Size(max = 120, message = "Address cannot exceed 120 characters")
    private String address;

    @NotBlank(message = "City is required")
    @Size(max = 60, message = "City cannot exceed 60 characters")
    private String city;

    @NotBlank
    @Size(max = 16, message = "ZIP code cannot exceed 16 characters")
    private String zip;

    @Email(message = "Please provide a valid email address")    // Could have used @Pattern instead
    @NotBlank(message = "Email is required")
    @Size(max = 120, message = "Email cannot exceed 120 characters")
    private String email;

    @NotBlank(message = "Username is required")
    @Size(min = 5, max = 40, message = "Username must be between 5 and 40 characters long")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
}
