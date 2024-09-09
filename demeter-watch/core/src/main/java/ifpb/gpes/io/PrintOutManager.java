package ifpb.gpes.io;

import ifpb.gpes.Call;
import ifpb.gpes.ExportManager;

import java.util.List;

/**
 * The {@code FileExportManager} class is an implementation of the {@link ExportManager} interface
 * that handles printing method call representations to standard output.
 */
public class PrintOutManager implements ExportManager {

    /**
     * Exports the method call representations to stdout.
     * @param elements List of {@link Call}.
     */
    @Override
    public void export(List<Call> elements) {
        elements.stream().forEach(no -> System.out.println(no.callGraph()));
    }
}