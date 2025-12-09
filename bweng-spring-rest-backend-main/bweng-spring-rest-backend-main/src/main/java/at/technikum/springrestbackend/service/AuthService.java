package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.TokenRequestDto;
import at.technikum.springrestbackend.dto.TokenResponseDto;
import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.security.jwt.TokenIssuer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Handles authentication and JWT token generation.
 * <p>
 * Validates user credentials using the AuthenticationManager and
 * issues a JWT token for successful logins.
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    private final TokenIssuer tokenIssuer;

    private final AuthenticationManager authenticationManager;

    /**
     * Authenticates the user and returns a JWT token.
     *
     * @param authRequest the login request containing username and password
     * @return a response containing the generated JWT
     */
    public TokenResponseDto authenticate(TokenRequestDto authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        String token = tokenIssuer.issue(principal.getId(), principal.getUsername(), principal.getRole());

        return new TokenResponseDto(token);
    }
}
