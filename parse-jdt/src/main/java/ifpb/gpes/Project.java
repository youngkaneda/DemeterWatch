package ifpb.gpes;

import ifpb.gpes.io.SmartFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 05/07/2017, 14:30:09
 */
public class Project {

    private String extensions = "";
    private SmartFile smart;
    private ProjectType type = new SmartProjectType();
    private final String root;

    private Project(String dir) {
        this.root = dir;
    }

    public static Project root(String dir) {
        return new Project(dir);
    }

    public Project type(ProjectType type) {
        this.type = type;
        return this;
    }

    public Project path(String path) {
        this.smart = SmartFile.from(Paths.get(root + path));
        return this;
    }

    public Project sources(String string) {
        this.type.addSources(string);
        return this;
    }

    public Stream<Path> files() {
        return this.smart.extension(extensions);
    }

    public String sources() {
        return this.root + this.type.sources();
    }

    public Project filter(String extension) {
        this.extensions = extension;
        return this;
    }

    private static interface ProjectType {

        void addSources(String source);

        String sources();
    }

    private static class SmartProjectType implements ProjectType {

        private String sources;

        @Override
        public void addSources(String source) {
            this.sources = source;
        }

        @Override
        public String sources() {
            return this.sources;
        }
    }

    public enum ProjectTypes implements ProjectType {
        MAVEN {
            @Override
            public void addSources(String source) {
            }

            @Override
            public String sources() {
                return "src/main/java/";
            }
        }
    }

}
