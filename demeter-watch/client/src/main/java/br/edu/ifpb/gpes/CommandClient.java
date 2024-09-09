package br.edu.ifpb.gpes;


import ifpb.gpes.Parse;
import ifpb.gpes.Project;
import ifpb.gpes.graph.io.BrokeExportManager;
import ifpb.gpes.jdt.ParseStrategies;
import ifpb.gpes.study.Study;
import picocli.CommandLine;
import java.io.File;
import java.util.concurrent.Callable;

/**
 * Command-line client for parsing a project and analyzing its collections
 * to identify violations of the Law of Demeter.
 */
@CommandLine.Command(
    description = "Parse a project in which its collections will be analyzed to catch breaks in the Law of Demeter.",
    name = "demeter-watch",
    version = {"1.0"}
)
public class CommandClient implements Callable<Void> {

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "Display help message.")
    private boolean usageHelp;

    @CommandLine.Option(names = {"-v", "--version"}, versionHelp = true, description = "Display version number.")
    private boolean versionHelp;

    @CommandLine.Option(names = {"-r", "--root"}, required = true, description = "The root path where the project resides.")
    private String root;

    @CommandLine.Option(names = {"-s", "--source"}, required = true, description = "The Java source code path used by the AST parser, e.g., 'path/to/java/files'. Relative to <root>.")
    private String source;

    @CommandLine.Option(names = {"-p", "--path"}, required = true, description = "The path of the project directory or the Java file to be analyzed, relative to <root>.")
    private String path;

    @CommandLine.Option(names = {"-o", "--output"}, required = true, description = "The path where the generated outputs will be created. The directory will be created if it does not exist.")
    private String outputDir;

    @CommandLine.Option(names = {"-c", "--classpath"}, split = " ", description = "List of classpath entries to be added. Only use if necessary for the tool to analyze code. Paths are relative to <path>.")
    private String[] classpath;

    /**
     * Main method to execute the command-line client.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        CommandLine cli = new CommandLine(new CommandClient());
        cli.setUsageHelpAutoWidth(true);
        int code = cli.execute(args);
        System.exit(code);
    }

    /**
     * Executes the command-line client logic.
     *
     * @return {@code null} as this method is required to return {@code Void}.
     */
    @Override
    public Void call() {
        Project project = Project
            .root(formatPath(root))
            .path(formatPath(path))
            .sources(formatPath(source))
            .classpath(classpath)
            .filter(".java");
        Study.of(project)
            .with(Parse.with(ParseStrategies.JDT))
            .analysis(new BrokeExportManager(outputDir))
            .execute();
        return null;
    }

    /**
     * Formats the given file path by ensuring it ends with a file separator.
     *
     * @param fpath The file path to format.
     * @return The formatted file path ending with a file separator if it wasn't already.
     */
    private String formatPath(String fpath) {
        return fpath.endsWith(File.separator) ? fpath : fpath + File.separator;
    }
}
