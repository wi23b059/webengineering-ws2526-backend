package at.technikum.springrestbackend.entity;

public enum Salutation {
    MR("Mr."),
    MS("Ms."),
    MRS("Mrs."),
    MX("Mx.");  // Gender-neutral

    private final String displayName;
    Salutation(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return name();  // Store MR, MS, ecc. in DB
    }
}
