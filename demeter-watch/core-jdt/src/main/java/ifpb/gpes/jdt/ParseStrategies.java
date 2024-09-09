package ifpb.gpes.jdt;


import ifpb.gpes.Call;
import ifpb.gpes.ParseStrategy;
import ifpb.gpes.Project;

import java.util.List;

/**
 * Enum representing different parsing strategies for analyzing Java projects.
 * <p>
 * This enum implements the {@link ParseStrategy} interface and provides specific strategies
 * for parsing Java code.
 * </p>
 *
 * @see ParseStrategy
 * @see SmartParseJDT
 */
public enum ParseStrategies implements ParseStrategy {

    /**
     * Parsing strategy using Eclipse JDT (Java Development Tools).
     * <p>
     * This strategy delegates the parsing process to an instance of {@link SmartParseJDT},
     * which handles the extraction of {@link Call} objects from a given {@link Project}.
     * </p>
     *
     * @param project The {@link Project} to parse.
     * @return A {@link List} of {@link Call} objects extracted from the project.
     */
    JDT {
        @Override
        public List<Call> from(Project project) {
            return new SmartParseJDT().from(project);
        }
    }
}
