package at.technikum.springrestbackend.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = GlobalExceptionHandlerTest.TestController.class)
@Import(GlobalExceptionHandler.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    /* ------------------------
       NOT FOUND (404)
     ------------------------ */
    @Test
    void shouldReturn404ForCategoryNotFound() throws Exception {
        mockMvc.perform(get("/test/not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error")
                        .value("Category with id 1 not found."));
    }

    /* ------------------------
       CONFLICT (409)
     ------------------------ */
    @Test
    void shouldReturn409ForEmailAlreadyExists() throws Exception {
        mockMvc.perform(get("/test/conflict"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error")
                        .value("Email already in use: test@example.com"));
    }

    /* ------------------------
       UNAUTHORIZED (401)
     ------------------------ */
    @Test
    void shouldReturn401ForBadCredentials() throws Exception {
        mockMvc.perform(get("/test/bad-credentials"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error")
                        .value("Bad credentials"));
    }

    /* ------------------------
       USERNAME NOT FOUND (404)
     ------------------------ */
    @Test
    void shouldReturn404ForUsernameNotFound() throws Exception {
        mockMvc.perform(get("/test/username-not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error")
                        .value("User not found"));
    }

    /* ------------------------
       VALIDATION ERROR (400)
     ------------------------ */
    @Test
    void shouldReturn400ForValidationErrors() throws Exception {
        mockMvc.perform(get("/test/validation"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name")
                        .value("must not be blank"));
    }

    /* -------------------------------------------------
       Dummy Controller only for testing ExceptionHandler
     ------------------------------------------------- */
    @RestController
    static class TestController {

        @GetMapping("/test/not-found")
        public void notFound() {
            throw new CategoryNotFoundException(1);
        }

        @GetMapping("/test/conflict")
        public void conflict() {
            throw new EmailAlreadyExistsException("test@example.com");
        }

        @GetMapping("/test/bad-credentials")
        public void badCredentials() {
            throw new BadCredentialsException("Bad credentials");
        }

        @GetMapping("/test/username-not-found")
        public void usernameNotFound() {
            throw new UsernameNotFoundException("User not found");
        }

        @GetMapping("/test/validation")
        public void validation(@Valid TestDto dto) {
            // will never be called
        }
    }

    static class TestDto {
        @NotBlank
        public String name;
    }
}
