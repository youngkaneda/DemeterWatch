package ifpb.gpes.study;

import ifpb.gpes.io.FileExportManager;
import ifpb.gpes.io.PrintOutManager;
import ifpb.gpes.Call;
import ifpb.gpes.ExportManager;
import ifpb.gpes.Parse;
import ifpb.gpes.ParseStrategy;
import ifpb.gpes.Project;

import java.util.Collections;
import java.util.List;

/**
 * The {@code Study} class is used to parse and export method call data from a given
 * {@link Project}. The class allows different export strategies and parsing approaches
 * to be configured dynamically.
 */
public class Study {

    private final ExportManager strategy;
    private final Parse parse;
    private final Project project;

    /**
     * Constructs a {@code Study} instance with default export strategy and parsing strategy.
     * The default export strategy prints output to the console and the default parsing strategy
     * is defined by {@link DefaultStrategy}.
     *
     * @param project the {@link Project} from which method calls will be parsed.
     */
    public Study(Project project) {
        this(new PrintOutManager(), Parse.with(new DefaultStrategy()), project);
    }

    /**
     * Constructs a {@code Study} instance with a specified export strategy, parsing strategy, and project.
     *
     * @param strategy the {@link ExportManager} to handle exporting the parsed method calls.
     * @param parse    the {@link Parse} instance used to parse the method calls from the project.
     * @param project  the {@link Project} to be parsed.
     */
    private Study(ExportManager strategy, Parse parse, Project project) {
        this.strategy = strategy;
        this.parse = parse;
        this.project = project;
    }

    /**
     * Executes the study by parsing method calls from the project and exporting the results
     * using the configured export strategy.
     */
    public void execute() {
        this.strategy.export(parse.from(project));
    }

    /**
     * Creates a new {@code Study} instance using the provided {@link Project}.
     *
     * @param project the project to be used in the study.
     * @return a new {@code Study} instance.
     */
    public static Study of(Project project) {
        return new Study(project);
    }

    /**
     * Creates a new {@code Study} instance with a custom {@link Parse} strategy.
     *
     * @param parse the custom {@link Parse} strategy to use.
     * @return a new {@code Study} instance with the specified parse strategy.
     */
    public Study with(Parse parse) {
        return new Study(this.strategy, parse, this.project);
    }

    /**
     * Creates a new {@code Study} instance with a custom export strategy.
     *
     * @param strategy the custom {@link ExportManager} to use for exporting.
     * @return a new {@code Study} instance with the specified export strategy.
     */
    public Study analysis(ExportManager strategy) {
        return new Study(strategy, this.parse, this.project);
    }

    /**
     * Creates a new {@code Study} instance that exports the parsed data to a file
     * with the specified file name.
     *
     * @param fileName the name of the file to export the results to.
     * @return a new {@code Study} instance that exports to a file.
     */
    public Study toFile(String fileName) {
        return new Study(FileExportManager.name(fileName + ".txt"), this.parse, this.project);
    }

    /**
     * The default strategy for parsing a project, which returns an empty list of {@link Call} objects.
     */
    private static class DefaultStrategy implements ParseStrategy {

        /**
         * Parses the project and returns an empty list of method calls.
         *
         * @param project the {@link Project} to parse.
         * @return an empty list of {@link Call} objects.
         */
        @Override
        public List<Call> from(Project project) {
            return Collections.EMPTY_LIST;
        }
    }
}
