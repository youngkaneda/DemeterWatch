package ifpb.gpes;

import java.util.List;

/**
 * Strategy interface for parsing data from a {@link Project}.
 * Implementations of this interface should provide the logic to convert a project into a list of {@link Call} objects.
 */
public interface ParseStrategy {

    /**
     * Parses data from the given {@link Project}.
     *
     * @param project The {@link Project} from which to parse data.
     * @return A list of {@link Call} objects representing the parsed data from the project.
     */
    public List<Call> from(Project project);
}
