package ifpb.gpes;

/**
 * Enumeration representing different types of project configurations.
 * This enum implements the {@link ProjectType} interface and provides specific configurations
 * for different types of projects.
 */
public enum ProjectTypes implements ProjectType {

    /**
     * Maven project type configuration.
     * Provides default implementation of the {@link ProjectType} interface methods
     * specific to Maven projects.
     */
    MAVEN {
        @Override
        public void addSources(String source) {
            // No implementation needed for Maven as sources are predefined.
        }

        @Override
        public String sources() {
            return "src/main/java/";
        }

        @Override
        public void addClasspath(String[] classpath) {
            // No implementation needed for Maven as classpath are predefined.
        }

        @Override
        public String[] classpath() {
            return null;
        }
    }
}
