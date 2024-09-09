package ifpb.gpes.graph;

import java.util.function.Supplier;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * Adapter class that converts a JGraphT {@code AbstractBaseGraph} into a {@code Matrix} representation.
 * <p>
 * This class implements the {@code Supplier} interface to provide a method for generating a {@code Matrix}
 * from a graph. The graph's nodes and edges are mapped to a matrix format, where the matrix cells represent
 * the weighted edges between nodes.
 * </p>
 */
public class AdapterMatrix implements Supplier<Matrix> {

    private Matrix matrix = new Matrix();
    private final AbstractBaseGraph<Node, DefaultWeightedEdge> graph;

    /**
     * Constructs an {@code AdapterMatrix} with the specified graph.
     *
     * @param graph the {@code AbstractBaseGraph} containing the nodes and edges to be transformed into a matrix.
     */
    public AdapterMatrix(AbstractBaseGraph<Node, DefaultWeightedEdge> graph) {
        this.graph = graph;
    }

    /**
     * Generates a {@code Matrix} from the graph's nodes and edges.
     * <p>
     * This method creates a matrix with cells representing the weights of edges between nodes.
     * If no edge exists between two nodes, the matrix cell is set to zero.
     * </p>
     *
     * @return the {@code Matrix} representing the graph.
     */
    @Override
    public Matrix get() {
        Node[] vertices = graph.vertexSet().toArray(new Node[]{});
        int numeroDeVertices = vertices.length;

        this.matrix = new Matrix(numeroDeVertices);

        for (int i = 0; i < numeroDeVertices; i++) {
            for (int j = 0; j < numeroDeVertices; j++) {
                Matrix.Cell cell = matrix.cell(i, j);
                DefaultWeightedEdge edge = edge(vertices, i, j);
                cell.set(weight(edge));
            }
            matrix.updateColumns(i, vertices[i]);
        }
        return this.matrix;
    }

    /**
     * Retrieves the edge between two nodes in the graph.
     *
     * @param vertices the array of nodes.
     * @param i the index of the first node.
     * @param j the index of the second node.
     * @return the {@code DefaultWeightedEdge} between the nodes, or {@code null} if no edge exists.
     */
    private DefaultWeightedEdge edge(Node[] vertices, int i, int j) {
        Node firstNode = vertices[i];
        Node secondNode = vertices[j];
        DefaultWeightedEdge edge = graph.getEdge(firstNode, secondNode);
        return edge;
    }

    /**
     * Returns the weight of the specified edge.
     *
     * @param edge the edge whose weight is to be retrieved.
     * @return the weight of the edge, or zero if the edge is {@code null}.
     */
    private int weight(DefaultWeightedEdge edge) {
        if (edge != null) {
            return (int) graph.getEdgeWeight(edge);
        } else {
            return 0;
        }
    }
}
