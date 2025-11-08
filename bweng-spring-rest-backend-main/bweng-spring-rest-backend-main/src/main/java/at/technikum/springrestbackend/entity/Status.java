package at.technikum.springrestbackend.entity;

/**
 * Represents the possible account states of a user.
 * <p>
 * Stored as a string in the database (e.g. "ACTIVE") via {@code @Enumerated(EnumType.STRING)}.
 */
public enum Status {
    ACTIVE,
    INACTIVE,
    BANNED,
    DELETED
}
