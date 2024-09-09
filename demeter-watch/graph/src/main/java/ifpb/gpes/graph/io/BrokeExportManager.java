package ifpb.gpes.graph.io;

import ifpb.gpes.Call;
import ifpb.gpes.ExportManager;
import ifpb.gpes.filter.FilterByMethod;
import ifpb.gpes.filter.FilterClassType;
import ifpb.gpes.graph.*;
import ifpb.gpes.io.FileExportManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * An implementation of {@link ExportManager} that exports information about method calls that violate the
 * Law of Demeter into text and JSON formats. It processes method calls, identifies calls that belong to Java
 * Collection Framework (JCF) classes, and detects calls that break confinement within a graph-based representation.
 * <p>
 * The export consists of:
 * <ul>
 *     <li>All method calls in the provided list.</li>
 *     <li>Method calls that belong to Java Collection Framework classes.</li>
 *     <li>Candidate method calls that are potential breaks in confinement.</li>
 *     <li>Method calls that actually break confinement.</li>
 * </ul>
 * </p>
 * The results are written to a text file and a JSON file. The text file contains a detailed report of method calls
 * and violations, while the JSON file contains a graph representation of the method call chains within the project.
 *
 * @see ExportManager
 * @see Call
 * @see Graph
 * @see Node
 * @see Matrix
 * @see JsonMatrix
 */
public class BrokeExportManager implements ExportManager {

    private final String CALLS_FILE_NAME = "calls.txt";
    private String outputDir;

    /**
     * Constructs a new {@code BrokeExportManager} with the specified output directory.
     *
     * @param outputDir The directory where the output files will be written.
     */
    public BrokeExportManager(String outputDir) {
        this.outputDir = outputDir;
    }

    /**
     * Exports the provided list of {@link Call} objects to files. The exported files include:
     * <ul>
     *     <li>A text file containing method call details and violations.</li>
     *     <li>A JSON file containing a graph representation of the method call chains within the project
     *         using weights to show how many times the method has been called.
     *     </li>
     * </ul>
     *
     * @param elements The list of {@link Call} objects to be exported.
     */
    @Override
    public void export(List<Call> elements) {
        File file = Paths.get(handleOutputFilePath(outputDir, "")).toFile();
        if (!file.exists()) {
            file.mkdirs();
        }

        StringBuilder result = new StringBuilder();

        result.append("All Method Calls (").append(elements.size()).append(")\n\n");
        elements.stream().map(Call::callGraph).forEach((call) -> result.append(call).append("\n"));

        Predicate<Call> predicate = new FilterClassType("java.util.Map").or(new FilterClassType("java.util.Collection"));

        long count = elements.stream().filter(predicate).count();

        result.append("\n\nCalls That Belongs To JCF (").append(count).append(")\n\n");
        elements.stream().filter(predicate).forEach((call) -> result.append(call.callGraph()).append("\n"));

        Graph graph = new AdapterGraph().apply(elements);

        List<Call> candidates = graph.getCandidates();

        predicate = predicate.and(elements::contains);

        candidates = candidates.stream().filter(predicate).collect(Collectors.toList());

        result.append("\n\nCalls That Are Candidates (").append(candidates.size()).append(")\n\n");
        candidates.forEach((call) -> result.append(call.callGraph()).append("\n"));

        candidates = candidates.stream().filter(new FilterByMethod()).collect(Collectors.toList());

        List<Integer> indices = new ArrayList<>();
        Matrix matrix = graph.toMatrix();
        List<Node> columnsList = Arrays.asList(matrix.getColumns());
        List<Call> brokers = new ArrayList<>();
        for (Call candidate : candidates) {
            for (Node node : columnsList) {
                if (nodeFromCall(candidate, node)) {
                    brokers.add(candidate);
                    indices.add(columnsList.indexOf(node));
                }
            }
        }

        result.append("\n\nCalls That Breaks Confinement (").append(brokers.size()).append(")\n\n");
        brokers.forEach((call) -> {
            result.append(call.callGraph()).append("\n");
        });
        write(result.toString(), Paths.get(handleOutputFilePath(outputDir, CALLS_FILE_NAME)));
        new JsonMatrix(matrix).toJson(indices, handleOutputFilePath(outputDir, ""));
    }

    /**
     * Writes the specified text to the given file path.
     * <p>
     * This method creates a new file or overwrites an existing file at the specified path with the provided text.
     * It uses a {@link BufferedWriter} to write the text content to the file. In case of an {@link IOException},
     * it logs an error message indicating the file path where the problem occurred.
     * </p>
     *
     * @param text The text content to be written to the file.
     * @param path The path to the file where the text will be written.
     */
    private void write(String text, Path path) {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(text);
        } catch (IOException ex) {
            Logger.getLogger(FileExportManager.class.getName()).log(Level.SEVERE, "Problem writing a file in " + path.getFileName().toString() + " path.");
        }
    }

    /**
     * Checks if a {@link Call} matches a {@link Node} based on method name, class type, return type, and invoking entity.
     *
     * @param call The {@link Call} object to be checked.
     * @param node The {@link Node} object to be matched against.
     * @return {@code true} if the {@link Call} matches the {@link Node}; {@code false} otherwise.
     */
    public boolean nodeFromCall(Call call, Node node) {
        if (!call.getMethodName().equals(node.getMethodName())) {
            return false;
        }
        if (!call.getClassType().equals(node.getClassName())) {
            return false;
        }
        if (!call.getReturnType().equals(node.getReturnType())) {
            return false;
        }
        if (!call.getInvokedBy().equals(node.getInvokedBy())) {
            return false;
        }
        return true;
    }
}
