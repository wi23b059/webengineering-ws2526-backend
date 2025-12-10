package at.technikum.springrestbackend.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * Security filter that authenticates requests based on a JWT bearer token.
 * <p>
 * For each incoming request, this filter:
 * <ul>
 *   <li>Reads the Authorization header</li>
 *   <li>Extracts and verifies the JWT</li>
 *   <li>Converts it into a UserPrincipal</li>
 *   <li>Stores an authenticated token in the SecurityContext</li>
 * </ul>
 * If no valid JWT is present, the filter does nothing and the request
 * continues unauthenticated.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_TYPE = "Bearer ";
    private final JwtDecoder jwtDecoder;
    private final JwtToPrincipalConverter jwtToPrincipalConverter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        extractTokenFromRequest(request)
                .map(jwtDecoder::decode)
                .map(jwtToPrincipalConverter::convert)
                .map(UserPrincipalAuthenticationToken::new)
                .ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));

        filterChain.doFilter(request, response);
    }


    /**
     * Extracts the raw JWT token from the Authorization header, if present.
     *
     * @param request the current HTTP request
     * @return an Optional containing the token without the "Bearer " prefix,
     *         or empty if no bearer token is found
     */
    private Optional<String> extractTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(token) && token.startsWith(BEARER_TYPE)) {
            return Optional.of(token.substring(BEARER_TYPE.length()));
        }

        return Optional.empty();
    }

}
