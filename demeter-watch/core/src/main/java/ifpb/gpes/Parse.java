package ifpb.gpes;

import java.util.List;

/**
 * The {@code Parse} class provides a method to convert a project into a list of {@link Call} objects.
 */
public class Parse {

    private final ParseStrategy type;

    /**
     * Private constructor for creating a {@code Parse} instance with the specified strategy.
     *
     * @param type The {@link ParseStrategy} used to parse data from a project.
     */
    private Parse(ParseStrategy type) {
        this.type = type;
    }

    /**
     * Factory method for creating a {@code Parse} instance with the given strategy.
     *
     * @param type The {@link ParseStrategy} to be used for parsing.
     * @return A new {@code Parse} instance configured with the provided strategy.
     */
    public static Parse with(ParseStrategy type) {
        return new Parse(type);
    }

    /**
     * Parses data from the provided {@link Project} using the configured {@link ParseStrategy}.
     *
     * @param project The {@link Project} to parse data from.
     * @return A list of {@link Call} objects representing the parsed data.
     */
    public List<Call> from(Project project) {
        return type.from(project);
    }
}
