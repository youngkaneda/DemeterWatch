package ifpb.gpes;

import ifpb.gpes.io.SmartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a project that contains configuration and file management for processing.
 * The {@code Project} class provides methods for setting up project directories, file types, and sources.
 */
public class Project {

    private String extensions = "";
    private SmartFile smart;
    private ProjectType type = new DefaultProjectType();
    private final String root;
    private String name = UUID.randomUUID().toString();

    /**
     * Protected constructor for creating a {@code Project} with the specified root directory.
     *
     * @param dir The root directory of the project.
     */
    protected Project(String dir) {
        this.root = dir;
    }

    /**
     * Factory method for creating a {@code Project} with the specified root directory.
     *
     * @param dir The root directory of the project.
     * @return A new {@code Project} instance.
     */
    public static Project root(String dir) {
        return new Project(dir);
    }

    /**
     * Sets the {@link ProjectType} for the project.
     *
     * @param type The {@link ProjectType} to be used for the project.
     * @return The current {@code Project} instance.
     */
    public Project type(ProjectType type) {
        this.type = type;
        return this;
    }

    /**
     * Sets the path for the project's files.
     *
     * @param path The relative path to the project's files.
     * @return The current {@code Project} instance.
     */
    public Project path(String path) {
        this.smart = SmartFile.from(Paths.get(root + path));
        return this;
    }

    /**
     * Sets the name of the project.
     *
     * @param name The name of the project.
     * @return The current {@code Project} instance.
     */
    public Project name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Adds source paths to the project's configuration.
     *
     * @param string The source paths to be added.
     * @return The current {@code Project} instance.
     */
    public Project sources(String string) {
        this.type.addSources(string);
        return this;
    }

    /**
     * Sets the classpath for the project.
     *
     * @param classpath An array of classpath entries.
     * @return The current {@code Project} instance.
     */
    public Project classpath(String[] classpath) {
        String[] correctedCp = Stream.ofNullable(classpath)
            .map(p -> Paths.get(smart.path().toAbsolutePath().toString(), p).toAbsolutePath().toString())
            .toList()
            .toArray(String[]::new);
        this.type.addClasspath(correctedCp);
        return this;
    }

    /**
     * Retrieves a stream of files with the specified extensions.
     *
     * @return A {@code Stream} of {@link Path} objects representing the files.
     */
    public Stream<Path> files() {
        return this.smart.extension(extensions);
    }

    /**
     * Retrieves the sources directory path.
     *
     * @return The sources directory path as a {@code String}.
     */
    public String sources() {
        return this.root + this.type.sources();
    }

    /**
     * Retrieves the classpath entries.
     *
     * @return An array of classpath entries.
     */
    public String[] classpath() {
        return this.type.classpath();
    }

    /**
     * Retrieves the name of the project.
     *
     * @return The project name as a {@code String}.
     */
    public String name() {
        return this.name;
    }

    /**
     * Sets a file extension filter for the project.
     *
     * @param extension The file extension to filter.
     * @return The current {@code Project} instance.
     */
    public Project filter(String extension) {
        this.extensions = extension;
        return this;
    }

    /**
     * Default implementation of the {@link ProjectType} interface.
     * This class provides basic methods for managing sources and classpath entries for a project.
     */
    private static class DefaultProjectType implements ProjectType {

        private String sources;
        private String[] classpath;

        /**
         * Adds source paths to this project type.
         *
         * @param source The source paths to be added.
         */
        @Override
        public void addSources(String source) {
            this.sources = source;
        }

        /**
         * Retrieves the source paths associated with this project type.
         *
         * @return The source paths as a {@code String}.
         */
        @Override
        public String sources() {
            return this.sources;
        }

        /**
         * Adds classpath entries to this project type.
         *
         * @param classpath An array of classpath entries to be added.
         */
        @Override
        public void addClasspath(String[] classpath) {
            this.classpath = classpath;
        }

        /**
         * Retrieves the classpath entries associated with this project type.
         *
         * @return An array of classpath entries.
         */
        @Override
        public String[] classpath() {
            return this.classpath;
        }
    }

}
