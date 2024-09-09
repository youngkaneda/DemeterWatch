package ifpb.gpes.graph;

import ifpb.gpes.Call;

import java.util.List;
import java.util.Set;

/**
 * Represents a generic graph structure with vertices and edges.
 * The graph provides methods to convert to a matrix, access vertices and edges,
 * check connectivity, and retrieve candidates based on {@code Call} objects.
 *
 * @param <V> the type of vertices in the graph
 * @param <E> the type of edges in the graph
 */
public interface Graph<V, E> {

    /**
     * Converts the graph to a matrix representation.
     *
     * @return a {@code Matrix} object representing the graph.
     */
    Matrix toMatrix();

    /**
     * Returns the set of vertices in the graph.
     *
     * @return a {@code Set} of vertices of type {@code V}.
     */
    public Set<V> vertex();

    /**
     * Returns the edge connecting the given source and target vertices.
     *
     * @param source the source vertex
     * @param target the target vertex
     * @return an edge of type {@code E} between the source and target vertices.
     */
    public E edge(V source, V target);

    /**
     * Checks if two vertices are connected by an edge.
     *
     * @param source the source vertex
     * @param target the target vertex
     * @return {@code true} if the source and target vertices are connected, {@code false} otherwise.
     */
    public boolean isConnected(V source, V target);

    /**
     * Retrieves a list of candidate calls based on graph analysis.
     *
     * @return a {@code List} of {@code Call} objects representing candidates.
     */
    public List<Call> getCandidates();
}