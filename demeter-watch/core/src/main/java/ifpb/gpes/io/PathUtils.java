package ifpb.gpes.io;

import java.io.File;

public class PathUtils {

    public static String connect(String... paths) {
        return String.join(File.separator, paths);
    }
}
