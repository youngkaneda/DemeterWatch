package br.edu.ifpb.gpes;


import ifpb.gpes.Parse;
import ifpb.gpes.Project;
import ifpb.gpes.graph.io.BrokeExportManager;
import ifpb.gpes.jdt.ParseStrategies;
import ifpb.gpes.study.Study;
import picocli.CommandLine;
import java.io.File;
import java.util.concurrent.Callable;

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

    @CommandLine.Option(names = {"-r", "--root"}, required = true, description = "The root path where the project are.")
    private String root;

    @CommandLine.Option(names = {"-s", "--source"}, required = true, description = "The java source code path that will be used by the AST parser, e.g.: 'path/to/java/files'. It is based on <root>")
    private String source;

    @CommandLine.Option(names = {"-p", "--path"}, required = true, description = "The path of the project directory, or the Java file to be analyzed, based on <root>.")
    private String path;

    @CommandLine.Option(names = {"-o", "--output"}, required = true, description = "The path where the generated outputs will be created. If not found, it will be created.")
    private String outputDir;

    @CommandLine.Option(names = {"-c", "--classpath"}, split = " ", description = "List of classpath to be added. The tool only work on code from the project, please use only if the tool break the analysis. The paths are based starting from <path>.")
    private String[] classpath;

    public static void main(String[] args) {
        CommandLine cli = new CommandLine(new CommandClient());
        cli.setUsageHelpAutoWidth(true);
        int code = cli.execute(args);
        System.exit(code);
    }

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

    private String formatPath(String fpath) {
        return fpath.endsWith(File.separator) ? fpath : fpath + File.separator;
    }
}
