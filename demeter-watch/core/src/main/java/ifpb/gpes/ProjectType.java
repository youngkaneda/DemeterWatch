package ifpb.gpes;

/**
 * Interface representing a type of project configuration.
 * This interface defines methods for managing source paths and classpath entries in a project.
 */
public interface ProjectType {

    /**
     * Adds source paths to the project type.
     *
     * @param source The source paths to be added.
     */
    void addSources(String source);

    /**
     * Retrieves the source paths associated with the project type.
     *
     * @return The source paths as a {@code String}.
     */
    String sources();

    /**
     * Adds classpath entries to the project type.
     *
     * @param classpath An array of classpath entries to be added.
     */
    void addClasspath(String[] classpath);

    /**
     * Retrieves the classpath entries associated with the project type.
     *
     * @return An array of classpath entries.
     */
    String[] classpath();
}