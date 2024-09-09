package ifpb.gpes.graph.io;

import ifpb.gpes.graph.Matrix;
import ifpb.gpes.graph.Node;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Handles the conversion of a {@link Matrix} to JSON format and generates necessary files for visualization.
 * <p>
 * This class provides functionality to generate JSON representations of nodes and edges in a matrix,
 * and create corresponding files for visualization purposes. It also includes methods to normalize strings
 * for JSON formatting and to copy resource files.
 * </p>
 */
public class JsonMatrix {

    private Matrix matrix;

    /**
     * Constructs a {@code JsonMatrix} with the specified {@link Matrix}.
     *
     * @param matrix The matrix to be converted to JSON.
     */
    public JsonMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    /**
     * Converts the matrix to JSON format and generates the necessary files for visualization.
     *
     * @param indices   The list of indices to highlight in the JSON output.
     * @param outputDir The directory where the generated files will be saved.
     */
    public void toJson(List<Integer> indices, String outputDir) {
        String nodes = nodesToJson(matrix, indices);
        String edges = edgesToJson(matrix);
        generateFiles(nodes, edges, matrix.namesColumns(), outputDir);
    }

    /**
     * Converts the edges of the matrix to JSON format.
     *
     * @param matrix The matrix containing the edges.
     * @return A JSON string representing the edges of the matrix.
     */
    private static String edgesToJson(Matrix matrix) {
        int[][] matrixs = matrix.toArray();
        return IntStream.range(0, matrixs.length)
            .mapToObj(x -> IntStream.range(0, matrixs.length)
                .filter(f -> matrixs[x][f] != 0)
                .mapToObj(y -> new EdgeVis(x, y, matrixs[x][y]))
                .collect(Collectors.toList()))
            .flatMap(v -> v.stream())
            .map(EdgeVis::toJson)
            .collect(Collectors.joining(", ", "[", "]"));
    }

    /**
     * Converts the nodes of the matrix to JSON format.
     *
     * @param matrix  The matrix containing the nodes.
     * @param indices The list of indices to highlight in the JSON output.
     * @return A JSON string representing the nodes of the matrix.
     */
    private String nodesToJson(Matrix matrix, List<Integer> indices) {
        String[] namesColumns = matrix.namesColumns();
        return IntStream.range(0, namesColumns.length)
            .filter(this.matrix::connected)
            .mapToObj((i) -> {
                Node node = matrix.getColumns()[i];
                if (indices.contains(i)) {
                    return String.format("{\"id\":\"%d\", \"label\":\"%s\", \"methodName\": \"%s\", \"className\": \"%s\", \"invokedBy\": \"%s\", "
                            + "\"font\": {\"color\": \"white\", \"bold\": true}, \"color\": {\"border\": \"black\", \"background\": \"red\"}}",
                        i, i, normalizeToJson(node.getMethodName()), normalizeToJson(node.getClassName()), normalizeToJson(node.getInvokedBy()));
                }
                return String.format("{\"id\":\"%d\", \"label\":\"%s\", \"methodName\": \"%s\", \"className\": \"%s\", \"invokedBy\": \"%s\"}",
                    i, i, normalizeToJson(node.getMethodName()), normalizeToJson(node.getClassName()), normalizeToJson(node.getInvokedBy()));
            })
            .collect(Collectors.joining(", ", "[", "]"));
    }

    /**
     * Normalizes a string for JSON formatting by escaping special characters.
     *
     * @param value The string to be normalized.
     * @return The normalized string suitable for JSON.
     */
    public String normalizeToJson(String value) {
        if (value == null) {
            return "";
        }
        value = value.replace("\\", "\\\\");
        value = value.replace("\"", "\\\"");
        value = value.replace("\n", "");
        return value;
    }

    /**
     * Generates the necessary files for visualization.
     *
     * @param nodes        The JSON representation of the nodes.
     * @param edges        The JSON representation of the edges.
     * @param namesColumns The names of the columns in the matrix.
     * @param outputDir    The directory where the files will be saved.
     */
    private void generateFiles(String nodes, String edges, String[] namesColumns, String outputDir) {
        Path script = Paths.get(outputDir + "script.js");
        Path elements = Paths.get(outputDir + "elements.json");
        Path page = Paths.get(outputDir + "graph.html");
        String elementsFile = "{" + "\"nodes\":" + nodes + "," + "\"edges\":" + edges + "}";
        createJson(elementsFile, elements);
        createFileCopy(page, "graph.html");
        createFileCopy(script, "script.js");
    }

    /**
     * Creates a JSON file with the specified content at the given path.
     *
     * @param text The content to be written to the file.
     * @param path The path where the file will be created.
     */
    private void createJson(String text, Path path) {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(text);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "problem writing file, the directory was not found or not exist.");
        }
    }

    /**
     * Copies a resource file to the specified path.
     *
     * @param path             The path where the resource file will be copied.
     * @param resourceFilename The name of the resource file to be copied.
     */
    private void createFileCopy(Path path, String resourceFilename) {
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream(resourceFilename);
            Files.copy(stream, path, REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "problem writing file, probably the directory was not found or not exist.");
        }
    }

    /**
     * Represents an edge in a graph for visualization purposes in JSON format for the vis.js library.
     * <p>
     * The {@code EdgeVis} class holds information about the source, destination, and label of an edge,
     * and provides functionality to convert this information into a JSON string suitable for visualization.
     * </p>
     */
    private static class EdgeVis {

        private final String from;
        private final String to;
        private final String label;
        private final String arrows = "to";

        /**
         * Constructs an {@code EdgeVis} object with the specified source node, destination node, and label.
         *
         * @param from  The source node of the edge.
         * @param to    The destination node of the edge.
         * @param label The label associated with the edge.
         */
        public EdgeVis(String from, String to, String label) {
            this.from = from;
            this.to = to;
            this.label = label;
        }

        /**
         * Constructs an empty {@code EdgeVis} object with default values.
         */
        public EdgeVis() {
            this("", "", "");
        }

        /**
         * Constructs an {@code EdgeVis} object with the specified source and destination node indices and label.
         *
         * @param from  The index of the source node.
         * @param to    The index of the destination node.
         * @param label The label associated with the edge.
         */
        private EdgeVis(int from, int to, int label) {
            this(String.valueOf(from),
                String.valueOf(to),
                String.valueOf(label));
        }

        /**
         * Returns a string representation of the edge.
         *
         * @return A string representing the edge, including the source, destination, and label.
         */
        @Override
        public String toString() {
            return "EdgeVis{" + "from=" + from + ", to=" + to + ", label=" + label + ", arrows=" + arrows + '}';
        }

        /**
         * Converts the edge information to a JSON string following the vis.js format.
         *
         * @return A JSON string representing the edge.
         */
        public String toJson() {
            return String.format("{\"from\":\"%s\", \"to\":\"%s\", \"arrows\":\"to\", \"label\":\"%s\"}", from, to, label);
        }
    }

}
