package ifpb.gpes.graph;

import ifpb.gpes.Call;
import java.util.*;
import java.util.stream.Collectors;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * DefaultDirectGraph is an implementation of a directed weighted graph using the JGraphT library.
 * <p>
 * Each {@code Call} represents a method invocation, which is modeled as {@code Node}s in the graph.
 * The graph tracks the method calls and their relationships, and offers functionality for finding
 * candidate calls and transforming the graph into a matrix representation.
 * </p>
 * <p>
 * This class supports adding method calls as nodes, checking for connectivity, and finding
 * shortest paths between nodes using Dijkstra's algorithm.
 * </p>
 */
public class DefaultDirectGraph implements Graph<Node, Double> {

    private final DefaultDirectedWeightedGraph<Node, DefaultWeightedEdge> graph =
        new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
    private final Stack<Node> nodes = new Stack<>();

    /**
     * Converts the graph into a matrix representation.
     *
     * @return a {@code Matrix} that represents the graph's vertices and edges.
     */
    @Override
    public Matrix toMatrix() {
        return new AdapterMatrix(graph).get();
    }

    /**
     * Builds and adds a new {@code Node} from the given {@code Call}.
     *
     * @param call the {@code Call} representing a method invocation.
     */
    public void buildNode(Call call) {
        Node firstNode = new Node();
        firstNode.setClassName(call.getClassType());
        firstNode.setMethodName(call.getMethodName());
        firstNode.setReturnType(call.getReturnType());
        firstNode.setInvokedBy(call.getInvokedBy());
        addNodeAsVertex(firstNode);

        if (isNotNullCallMethod(call)) {
            Node get = nodes.pop();
            addNodeAsVertex(get);
            updateNodesToGraph(firstNode, get);

            if (isInvokedByMethod(call)) {
                nodes.push(firstNode);
            } else {
                Node second = nodes.pop();
                addNodeAsVertex(second);
                updateNodesToGraph(second, firstNode);
            }
        } else {
            Node secondNode = new Node();
            secondNode.setClassName(call.getCalledInClass());
            secondNode.setMethodName(call.getCalledInMethod());
            secondNode.setReturnType(call.getCalledInMethodReturnType());
            nodes.push(secondNode);
            nodes.push(firstNode);
        }
    }

    /**
     * Retrieves a list of candidate {@code Call}`s that represent the shortest paths from source to target nodes.
     *
     * @return a {@code List} of {@code Call} objects that are candidates of breaks in the LoD based on the graph's structure.
     */
    @Override
    public List<Call> getCandidates() {
        List<Node> sources = graph.vertexSet().stream().filter((n) -> graph.incomingEdgesOf(n).isEmpty() && !graph.outgoingEdgesOf(n).isEmpty()).toList();
        List<Node> leaves = graph.vertexSet().stream().filter((n) -> graph.outgoingEdgesOf(n).isEmpty() && !graph.incomingEdgesOf(n).isEmpty()).toList();
        List<Call> mountedCalls = new ArrayList<>();
        sources.parallelStream().forEach(source -> {
            leaves.parallelStream().forEach(leaf -> {
                DijkstraShortestPath<Node, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(graph);
                GraphPath<Node, DefaultWeightedEdge> shortestPath = dijkstra.getPath(source, leaf);
                if (shortestPath != null) {
                    Call mountedCall = mountCall(shortestPath.getStartVertex(), shortestPath.getEndVertex());
                    if (!isInvokedByThis(mountedCall.getInvokedBy())) {
                        mountedCalls.add(mountedCall);
                    }
                }
            });
        });
        return mountedCalls;
    }

    /**
     * Creates a {@code Call} object based on the provided start and end nodes.
     *
     * @param start the start {@code Node}.
     * @param end the end {@code Node}.
     * @return a {@code Call} representing the method call from start to end node.
     */
    private Call mountCall(Node start, Node end) {
        return Call.of(
            end.getClassName(), end.getMethodName(), end.getReturnType(),
            start.getClassName(), start.getMethodName(), start.getReturnType(), null, end.getInvokedBy()
        );
    }

    /**
     * Retrieves all the vertices in the graph.
     *
     * @return a {@code Set} of {@code Node} vertices.
     */
    @Override
    public Set<Node> vertex() {
        return graph.vertexSet();
    }

    /**
     * Retrieves the weight of the edge between two nodes.
     *
     * @param source the source {@code Node}.
     * @param target the target {@code Node}.
     * @return the weight of the edge, or {@code 0d} if no edge exists.
     */
    @Override
    public Double edge(Node source, Node target) {
        if (isConnected(source, target)) {
            DefaultWeightedEdge edge = graph.getEdge(source, target);
            return graph.getEdgeWeight(edge);
        }
        return 0d;
    }

    /**
     * Checks if a method call is invoked by "this" (i.e., self-invocation).
     *
     * @param invokedBy the string representation of the invoker.
     * @return {@code true} if invoked by "this", otherwise {@code false}.
     */
    private boolean isInvokedByThis(String invokedBy) {
        if (!invokedBy.contains(".")) {
            return true;
        }
        String[] parts = invokedBy.split("\\.");
        return parts.length >= 2 && "this".equals(parts[0]);
    }

    /**
     * Checks if the {@code Call} is invoked by a method.
     *
     * @param call the {@code Call} to check.
     * @return {@code true} if invoked by a method, otherwise {@code false}.
     */
    private boolean isInvokedByMethod(Call call) {
        String invokedBy = call.getInvokedBy();
        if (invokedBy == null || invokedBy.equals("this")) {
            return false;
        }
        return invokedBy.endsWith(")") && !invokedBy.contains("new");
    }

    /**
     * Adds a node as a vertex in the graph if it does not already exist.
     *
     * @param node the {@code Node} to add as a vertex.
     */
    private void addNodeAsVertex(Node node) {
        if (!graph.containsVertex(node)) {
            graph.addVertex(node);
        }
    }

    /**
     * Updates the graph by adding an edge between two nodes or incrementing the edge weight if it already exists.
     *
     * @param first the first {@code Node}.
     * @param second the second {@code Node}.
     */
    private void updateNodesToGraph(Node first, Node second) {
        if (!graph.containsEdge(first, second)) {
            DefaultWeightedEdge addEdge = graph.addEdge(first, second);
            graph.setEdgeWeight(addEdge, 1);
        } else {
            DefaultWeightedEdge edge = graph.getEdge(first, second);
            graph.setEdgeWeight(edge, graph.getEdgeWeight(edge) + 1);
        }
    }

    /**
     * Checks if the callMethod in the {@code Call} object is not null.
     *
     * @param call the {@code Call} to check.
     * @return {@code true} if the method to be called is not null, otherwise {@code false}.
     */
    private boolean isNotNullCallMethod(Call call) {
        return call.getCallMethod() != null && !"null".equals(call.getCallMethod().trim());
    }

    /**
     * Checks if there is a direct connection between two nodes.
     *
     * @param source the source {@code Node}.
     * @param target the target {@code Node}.
     * @return {@code true} if the nodes are connected, otherwise {@code false}.
     */
    @Override
    public boolean isConnected(Node source, Node target) {
        return graph.getEdge(source, target) != null;
    }
}
