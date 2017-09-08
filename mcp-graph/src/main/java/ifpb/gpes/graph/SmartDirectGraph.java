package ifpb.gpes.graph;

import ifpb.gpes.Call;
import ifpb.gpes.graph.Graph;
import ifpb.gpes.graph.Matrix;
import ifpb.gpes.graph.Node;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

/**
 *
 * @author juan
 */
public class SmartDirectGraph implements Graph{

    private final SimpleDirectedWeightedGraph<Node, DefaultWeightedEdge> graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
    private Matrix matrix = new Matrix();

    public SimpleDirectedWeightedGraph<Node, DefaultWeightedEdge> getGraph() {
        return graph;
    }

    @Override
    public void adicionarNos(Call call) {
        Node firstnode = new Node();
        Node secondnode = new Node();

        firstnode.setClassName(call.getClassType());
        firstnode.setMethodName(call.getMethodName());
        firstnode.setReturnType(call.getReturnType());

        if (!graph.containsVertex(firstnode)) {
            graph.addVertex(firstnode);
        }

        secondnode.setClassName(call.getCalledInClass());
        secondnode.setMethodName(call.getCallMethod());
        secondnode.setReturnType("...");

        if (!graph.containsVertex(secondnode)) {
            graph.addVertex(secondnode);
        }

        if (!graph.containsEdge(firstnode, secondnode)) {
            DefaultWeightedEdge addEdge = graph.addEdge(firstnode, secondnode);
            graph.setEdgeWeight(addEdge, 1);
        } else {
            DefaultWeightedEdge edge = graph.getEdge(firstnode, secondnode);
            graph.setEdgeWeight(edge, graph.getEdgeWeight(edge) + 1);
        }
    }

    @Override
    public Matrix generateMatrix(){

        Node[] vertices = graph.vertexSet().toArray(new Node[]{});
        int numeroDeVertices = vertices.length;

        this.matrix = new Matrix(numeroDeVertices);

        for (int i = 0; i < numeroDeVertices; i++) {
            for (int j = 0; j < numeroDeVertices; j++) {
                Matrix.Cell cell = matrix.cell(i, j);
                DefaultWeightedEdge edge = edge(vertices, i, j);
                cell.set(weight(edge));
                System.out.println(cell);
            }
        }

        return this.matrix;
//         Integer[][] array = IntStream.range(0, 2)
//                .mapToObj(linha -> {
//                    return IntStream.range(0, 2)
//                            .mapToObj(coluna -> {
//                                int a = matrix[linha][coluna];
//                                if (a > 0) {
//                                    return matrix[linha][coluna] + 1;
//                                }
//                                return matrix[linha][coluna];
//                            })
//                            .toArray(Integer[]::new);
//                })
//                .toArray(Integer[][]::new);
    }

    private DefaultWeightedEdge edge(Node[] vertices, int i, int j) {
        Node firstNode = vertices[i];
        Node secondNode = vertices[j];
        DefaultWeightedEdge edge = graph.getEdge(firstNode, secondNode);
        return edge;
    }

    private int weight(DefaultWeightedEdge edge) {
        if (edge != null) {
            return (int) graph.getEdgeWeight(edge);
        } else {
            return 0;
        }
    }

}
