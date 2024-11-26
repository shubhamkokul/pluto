package castiel.solutionbyhour.model.data;

/**
 * Enum representing the form of alert change.
 */
public enum AlertChangeForm {
    POSITIVE("Positive Change"),
    NEGATIVE("Negative Change"),
    CHANGE("General Change");

    private final String description;

    /**
     * Constructor to associate a description with each enum constant.
     *
     * @param description A human-readable description of the alert change form.
     */
    AlertChangeForm(String description) {
        this.description = description;
    }

    /**
     * Gets the description of the alert change form.
     *
     * @return The description associated with the enum constant.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Overrides the default toString() method to return the description.
     *
     * @return The description of the alert change form.
     */
    @Override
    public String toString() {
        return description;
    }

    /**
     * Utility method to get an enum constant by its name, case-insensitively.
     *
     * @param name The name of the enum constant.
     * @return The matching AlertChangeForm, or null if no match is found.
     */
    public static AlertChangeForm fromName(String name) {
        for (AlertChangeForm form : values()) {
            if (form.name().equalsIgnoreCase(name)) {
                return form;
            }
        }
        return null; // or throw an IllegalArgumentException if you prefer
    }
}