package at.technikum.springrestbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response body returned after a successful login.
 * <p>
 * Contains the JWT token that the client must send with future requests.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponseDto {
    private String token;
}
