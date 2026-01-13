package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.entity.Salutation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequestDto {
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

    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$",
            message = "Password must be at least 8 characters long and contain digits, lowercase and uppercase letters"
    )
    private String password;

    /**
     * Optional profile picture URL; if null or blank, a default placeholder is used on response.
     */
    @Size(max = 255, message = "Profile picture URL cannot exceed 255 characters")
    private String profilePicturePath;
}
