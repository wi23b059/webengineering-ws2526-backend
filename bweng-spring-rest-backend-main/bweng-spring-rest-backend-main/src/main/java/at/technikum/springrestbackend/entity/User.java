package at.technikum.springrestbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

/**
 * Represents an application user persisted in the database.
 * <p>
 * Each instance corresponds to a record in the {@code users} table.
 * The class defines personal information (name, contact), authentication data
 * (username, password), and account state (role, status).
 * <p>
 * Additional metadata such as creation and update timestamps are automatically
 * managed by Hibernate through {@code @CreationTimestamp} and {@code @UpdateTimestamp}.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "users",
        // Still need to check for uniqueness at application level
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_users_email", columnNames = "email"),
                @UniqueConstraint(name = "unique_users_username", columnNames = "username")
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "salutation", length = 16)
    private Salutation salutation;

    @Column(name = "first_name", nullable = false, length = 60)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 60)
    private String lastName;

    @Column(name = "country_code", length = 2, nullable = false)
    private String countryCode;

    @Column(name = "address", nullable = false, length = 120)
    private String address;

    @Column(name = "zip", length = 16)
    private String zip;

    @Column(name = "city", nullable = false, length = 60)
    private String city;

    @Column(name = "email", nullable = false, unique = true, length = 120)
    private String email;

    @Column(name = "username", nullable = false, unique = true, length = 40)
    private String username;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Optional URL or path to the user's profile picture.
     * If null, the API should respond with a default placeholder URL.
     */
    @Column(name = "profile_picture_url", length = 255)
    private String profilePictureUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 16, nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 16, nullable = false)
    private Status status;

    // UTC, time-zone independent compared to LocalDateTime
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    // UTC, time-zone independent compared to LocalDateTime
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}
