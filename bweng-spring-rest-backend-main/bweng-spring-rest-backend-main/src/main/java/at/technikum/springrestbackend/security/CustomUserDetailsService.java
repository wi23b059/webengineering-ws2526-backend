package at.technikum.springrestbackend.security;

import at.technikum.springrestbackend.entity.User;
import at.technikum.springrestbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Loads user details for Spring Security authentication.
 * <p>
 * Allows users to log in using either their username or email.
 * Converts the application's User entity into a UserPrincipal,
 * which Spring Security uses during authentication and authorization.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    /**
     * Finds a user by username or email and returns a UserPrincipal.
     *
     * @param usernameOrEmail the username or email provided during login
     * @return a UserPrincipal containing the user's security information
     * @throws UsernameNotFoundException if no matching user is found
     */
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // Search first by username, then by email
        User user = userService.findByUsernameOptional(usernameOrEmail)
                .or(() -> userService.findByEmailOptional(usernameOrEmail))
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found (username or email): " + usernameOrEmail)
                );

        // UserPrincipal expects UUID, username, password and Role
        return new UserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole()
        );
    }
}
