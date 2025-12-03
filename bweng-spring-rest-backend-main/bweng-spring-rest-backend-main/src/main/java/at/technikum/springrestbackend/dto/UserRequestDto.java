package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.entity.Salutation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @NotBlank(message = "Country code is required")
    @Pattern(
            regexp = "^[A-Z]{2}$",
            message = "Country code must be a valid alpha-2 code (ex: AT, IT, DE)"
    )
    private String countryCode;

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
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$",
            message = "Password must be at least 8 characters long and contain digits, lowercase and uppercase letters"
    )
    private String password;

    /**
     * Optional profile picture URL; if null or blank, a default placeholder is used on response.
     */
    @Size(max = 255, message = "Profile picture URL cannot exceed 255 characters")
    private String profilePictureUrl;
}
