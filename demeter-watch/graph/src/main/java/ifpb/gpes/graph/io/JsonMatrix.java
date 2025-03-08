package ifpb.gpes.graph.io;

import ifpb.gpes.graph.Matrix;
import ifpb.gpes.graph.Node;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
        boolean smallerOutput = Boolean.parseBoolean(System.getProperty("reducedGraph"));
        Collection<EdgeVis> edges;
        edges = smallerOutput ? buildEdges(matrix, indices) : buildEdges(matrix);
        String nodesJson = nodesToJson(
            matrix,
            indices,
            smallerOutput
                ? edges.stream().flatMap(e -> Stream.of(Integer.valueOf(e.from), Integer.valueOf(e.to))).collect(Collectors.toSet())
                : Collections.emptySet()
            );
        String edgesJson = edges.stream().map(EdgeVis::toJson).collect(Collectors.joining(", ", "[", "]"));
        generateFiles(nodesJson, edgesJson, matrix.namesColumns(), outputDir);
    }

    /**
     * Converts the edges of the matrix to collection of objects.
     *
     * @param matrix The matrix containing the edges.
     * @return A Collection of {@link EdgeVis} objects representing the edges of the matrix.
     */
    private static Collection<EdgeVis> buildEdges(Matrix matrix) {
        int[][] matrixs = matrix.toArray();
        return IntStream.range(0, matrixs.length)
            .mapToObj(x -> IntStream.range(0, matrixs.length)
                .filter(f -> matrixs[x][f] != 0)
                .mapToObj(y -> new EdgeVis(x, y, matrixs[x][y]))
                .collect(Collectors.toList()))
            .flatMap(Collection::stream).collect(Collectors.toList());
    }

    /**
     * Converts the edges of the matrix to collection of objects.
     *
     * @param matrix The matrix containing the edges.
     * @param indices The List with the indices of calls that break the LoD.
     * @return A Collection of {@link EdgeVis} objects representing the edges of the matrix.
     */
    private static Collection<EdgeVis> buildEdges(Matrix matrix, List<Integer> indices) {
        int[][] matrixs = matrix.toArray();
        int nextIndex;
        Set<EdgeVis> edges = new HashSet<>();
        for (int j = 0; j < matrixs.length; j++) {
            if (indices.contains(j)) {
                nextIndex = j;
                while (nextIndex != -1) {
                    for (int i = 0; i < matrixs.length; i++) {
                        if (matrixs[i][nextIndex] == 0) {
                            if (i == matrixs.length - 1) {
                                nextIndex = -1;
                            }
                            continue;
                        }
                        if (matrixs[i][nextIndex] != 0) {
                            edges.add(new EdgeVis(i, nextIndex, matrixs[i][nextIndex]));
                            nextIndex = i;
                            break;
                        }
                    }
                }
            }
        }
        return edges;
    }

    /**
     * Converts the nodes of the matrix to JSON format.
     *
     * @param matrix  The matrix containing the nodes.
     * @param indices The list of indices to highlight in the JSON output.
     * @param edgesToLookup The list of edges created, if the user selected a smaller output it will
     * only produce the node representation of nodes with connections to LoD breaks.
     * @return A JSON string representing the nodes of the matrix.
     */
    private String nodesToJson(Matrix matrix, List<Integer> indices, Collection<Integer> edgesToLookup) {
        String[] namesColumns = matrix.namesColumns();
        return IntStream.range(0, namesColumns.length)
            .filter(this.matrix::connected)
            .filter(i -> edgesToLookup.isEmpty() || edgesToLookup.contains(i))
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EdgeVis edgeVis = (EdgeVis) o;
            if (!Objects.equals(from, edgeVis.from)) return false;
            if (!Objects.equals(to, edgeVis.to)) return false;
            return Objects.equals(label, edgeVis.label);
        }

        @Override
        public int hashCode() {
            int result = from != null ? from.hashCode() : 0;
            result = 31 * result + (to != null ? to.hashCode() : 0);
            result = 31 * result + (label != null ? label.hashCode() : 0);
            return result;
        }
    }

}
