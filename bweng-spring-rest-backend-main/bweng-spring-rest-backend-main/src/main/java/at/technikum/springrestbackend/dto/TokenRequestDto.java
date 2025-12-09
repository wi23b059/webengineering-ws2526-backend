package at.technikum.springrestbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Request body for the login endpoint.
 * <p>
 * Contains the username (or email) and password needed to authenticate
 * and generate a JWT token.
 */
@Getter
@Setter
public class TokenRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
