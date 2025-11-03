package at.technikum.springrestbackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String salutation;
    private String first_name;
    private String last_name;
    private String address;
    private String zip;
    private String city;
    private String email;
    private String username;
    private String password;
    private String role;
    private String status;

    // Getter + Setter generated with Lombok @Data annotation
}
