package at.technikum.springrestbackend.entity;

/**
 * Represents the role of a user in the system.
 * Stored as a string in the database thanks to @Enumerated(EnumType.STRING) in User.java.
 */
public enum Role {
    USER,
    ADMIN;
}
