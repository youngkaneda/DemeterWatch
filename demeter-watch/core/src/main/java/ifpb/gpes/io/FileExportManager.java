package ifpb.gpes.io;

import ifpb.gpes.Call;
import ifpb.gpes.ExportManager;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * The {@code FileExportManager} class is an implementation of the {@link ExportManager} interface
 * that handles exporting method call representations to a file. The file is written using a
 * specified name and contains a serialized version of the calls.
 */
public class FileExportManager implements ExportManager {

    private String name;

    /**
     * Constructor that accepts a file name.
     * @param name Name to the file that will be written.
     */
    protected FileExportManager(String name) {
        this.name = name;
    }

    /**
     * Statically generates an instance.
     * @param name Name of the file that will be written.
     * @return An instance of this class.
     */
    public static ExportManager name(String name) {
        return new FileExportManager(name);
    }

    /**
     * Exports the method call representations to a file.
     * @param elements List of {@link Call}.
     */
    @Override
    public void export(List<Call> elements) {
        String text = elements.stream()
            .map(Call::callGraph)
            .collect(Collectors.joining("\n"));
        write(text);
    }

    /**
     * Writes text into file with name {@link FileExportManager#name}.
     * @param text content to be written.
     */
    protected void write(String text) {
        Path path = Paths.get(name);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(text);
        } catch (IOException ex) {
            Logger.getLogger(FileExportManager.class.getName()).log(Level.SEVERE, "problem write file", ex);
        }
    }
}
