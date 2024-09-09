package ifpb.gpes.io;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * The {@code SmartFile} class provides utility methods to handle file
 * operations, such as filtering files based on their extension, and
 * recursively traversing directories to find files. This class is
 * built around a {@link Path} object and supports walking through
 * directory trees.
 *
 * <p>Example usage:
 * <pre>
 *     SmartFile sf = SmartFile.from(somePath);
 *     sf.files().forEach(System.out::println);
 * </pre>
 */
public class SmartFile {

    private final Path path;

    /**
     * Private constructor to initialize a {@code SmartFile} instance.
     * Use the static factory method {@link #from(Path)} to create an instance.
     *
     * @param path the {@code Path} associated with the SmartFile
     */
    private SmartFile(Path path) {
        this.path = path;
    }

    /**
     * Creates and returns a {@code SmartFile} object associated with the given {@code Path}.
     *
     * @param path the {@code Path} to associate with the SmartFile
     * @return a new {@code SmartFile} instance
     * @throws IllegalArgumentException if the {@code path} is null
     */
    public static SmartFile from(Path path) {
        if (path == null) {
            throw new IllegalArgumentException("path is null");
        }
        return new SmartFile(path);
    }

    /**
     * Returns a stream of all files in the directory tree rooted at the {@code Path} associated
     * with this {@code SmartFile} instance, without filtering by extension.
     *
     * @return a {@code Stream} of {@code Path} objects representing the files found
     */
    public Stream<Path> files() {
        return extension("");
    }

    /**
     * Returns a stream of files with a specific extension in the directory tree rooted at
     * the {@code Path} associated with this {@code SmartFile} instance.
     *
     * @param extensionFile the extension to filter files by (e.g. ".txt", ".java")
     * @return a {@code Stream} of {@code Path} objects representing the filtered files
     */
    public Stream<Path> extension(String extensionFile) {
        List<Path> paths = new ArrayList<>();

        try {
            Files.walkFileTree(this.path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (fileWithExtension(file, extensionFile)) {
                        paths.add(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(SmartFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paths.stream();
    }

    /**
     * Checks if the given file has the specified extension.
     *
     * @param file the file to check
     * @param extension the extension to match (e.g. ".txt", ".java")
     * @return {@code true} if the file has the given extension, {@code false} otherwise
     */
    private boolean fileWithExtension(Path file, String extension) {
        return file.toFile()
            .getName()
            .endsWith(extension);
    }

    /**
     * Returns the {@code Path} associated with this {@code SmartFile} instance.
     *
     * @return the {@code Path} object
     */
    public Path path() {
        return path;
    }

    /**
     * A {@code FileFilter} implementation that filters files based on their extensions.
     * This filter accepts directories and files that match any of the specified extensions.
     */
    class FileTypesFilter implements FileFilter {

        String[] types;

        /**
         * Constructs a {@code FileTypesFilter} that filters files by the given array of extensions.
         *
         * @param types the array of file extensions to filter (e.g. ".txt", ".java")
         */
        FileTypesFilter(String[] types) {
            this.types = types;
        }

        /**
         * Determines whether a file or directory should be accepted by this filter.
         *
         * @param f the {@code File} to test
         * @return {@code true} if the file is a directory or matches one of the extensions, {@code false} otherwise
         */
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            for (String type : types) {
                if (f.getName().endsWith(type)) {
                    return true;
                }
            }
            return false;
        }
    }
}
