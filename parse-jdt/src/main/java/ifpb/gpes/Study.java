package ifpb.gpes;

import ifpb.gpes.io.FileExportManager;
import ifpb.gpes.io.PrintOutManager;
import ifpb.gpes.jdt.ParseStrategies;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 07/07/2017, 14:44:47
 */
public class Study {

    private final ExportManager strategy;
    private final Parse parse;
    private final Project project;

    private Study(ExportManager strategy, Parse parse, Project project) {
        this.strategy = strategy;
        this.parse = parse;
        this.project = project;
    }

    public void execute() {
        this.strategy.export(parse.from(project));
    }

    public static Study of(Project project) {
        return new Study(new PrintOutManager(), Parse.with(ParseStrategies.JDT), project);
    }

    public Study with(Parse parse) {
        return new Study(this.strategy, parse, this.project);
    }

    public Study analysis(ExportManager strategy) {
        return new Study(strategy, this.parse, this.project);
    }

    public Study toFile(String fileName) {
        return new Study(FileExportManager.name(fileName + ".txt"), this.parse, this.project);
    }

}
