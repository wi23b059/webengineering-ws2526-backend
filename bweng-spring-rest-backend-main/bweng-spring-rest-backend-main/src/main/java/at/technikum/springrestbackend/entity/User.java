package at.technikum.springrestbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "salutation", length = 16)
    private Salutation salutation;

    @Column(name = "first_name", nullable = false, length = 60)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 60)
    private String lastName;

    @Column(name = "address", nullable = false, length = 120)
    private String address;

    @Column(name = "zip", length = 16)
    private String zip;

    @Column(name = "city", nullable = false, length = 60)
    private String city;

    @Column(name = "email", nullable = false, length = 120)
    private String email;

    @Column(name = "username", nullable = false, length = 40)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    // Change to enum?
    private String role;

    // Change to enum?
    private String status;

    // UTC, time-zone independent compared to LocalDateTime
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    // UTC, time-zone independent compared to LocalDateTime
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}
