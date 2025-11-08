package at.technikum.springrestbackend.entity;

/**
 * Represents the possible salutations (titles) a user can have.
 * <p>
 * Each constant corresponds to a value stored in the database (MR, MS, MRS, MX)
 */
public enum Salutation {
    MR("Mr."),
    MS("Ms."),
    MRS("Mrs."),
    MX("Mx.");  // Gender-neutral, because it's 2025

    private final String displayName;

    /**
     * Creates a new salutation with its human-readable form.
     *
     * @param displayName the formatted version of the salutation, such as "Mr." or "Ms."
     */
    Salutation(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the human-readable version of this salutation.
     *
     * @return the display name, such as "Mr." or "Ms."
     */
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        // Store MR, MS, ecc. in DB
        return name();
    }
}
