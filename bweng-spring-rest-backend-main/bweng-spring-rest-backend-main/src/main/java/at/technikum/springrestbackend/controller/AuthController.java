package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.TokenRequestDto;
import at.technikum.springrestbackend.dto.TokenResponseDto;
import at.technikum.springrestbackend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for authentication endpoints.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Authenticates a user using username or email and password,
     * then returns a JWT. This is the main login endpoint.
     */
    @PostMapping("/token")
    public TokenResponseDto token(@RequestBody @Valid final TokenRequestDto tokenRequestDto) {
        return authService.authenticate(tokenRequestDto);
    }
}