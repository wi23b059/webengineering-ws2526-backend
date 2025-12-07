package at.technikum.springrestbackend.security;

import at.technikum.springrestbackend.entity.User;
import at.technikum.springrestbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // Suche zuerst nach Username, dann nach Email
        User user = userService.findByUsernameOptional(usernameOrEmail)
                .or(() -> userService.findByEmailOptional(usernameOrEmail))
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found (username or email): " + usernameOrEmail)
                );

        // UserPrincipal erwartet UUID, username, password und Role
        return new UserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole()
        );
    }
}
