package ifpb.gpes.io;

import java.io.File;

public class PathUtils {

    /**
     * Connects file paths in a platform-independent manner by using the system's
     * file separator. This ensures that the correct separator (e.g., "/" or "\\")
     * is used based on the operating system.
     *
     * @param paths a variable number of {@code String} file paths to be concatenated.
     * @return a single concatenated path with the input paths connected by the
     * system's file separator.
     */    public static String connect(String... paths) {
        return String.join(File.separator, paths);
    }
}
