    package at.technikum.springrestbackend.security.jwt;

    import at.technikum.springrestbackend.entity.Role;
    import org.springframework.stereotype.Component;

    import java.util.UUID;

    public interface TokenIssuer {
        String issue(UUID userId, String username, Role role);
    }