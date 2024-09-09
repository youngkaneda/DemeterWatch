package ifpb.gpes;

import java.io.File;
import java.util.List;

/**
 * Interface for exporting method call representations.
 * Implementations of this interface should provide functionality to export
 * a list of {@link Call} objects to a specific destination (e.g., file, console).
 */
public interface ExportManager {

    /**
     * Exports a list of method call representations.
     *
     * @param elements The list of {@link Call} objects to be exported.
     */
    public void export(List<Call> elements);

    /**
     * Generates the output file path based on the provided directory and file name.
     * This method ensures the correct file separator is used, depending on whether the
     * directory ends with a file separator or not.
     *
     * @param dir The directory where the file will be saved.
     * @param filename The name of the output file.
     * @return The complete file path combining the directory and filename.
     */
    public default String handleOutputFilePath(String dir, String filename) {
        return dir.endsWith(File.separator) ? dir + filename : dir + File.separator + filename;
    }
}