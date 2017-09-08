package ifpb.gpes.graph;

import br.edu.ifpb.gpes.graph.DirectGraph;
import br.edu.ifpb.gpes.mcp.core.Call;
import br.edu.ifpb.gpes.mcp.core.Parse;
import br.edu.ifpb.gpes.mcp.core.Project;
import br.edu.ifpb.gpes.mcp.strategies.ParseStrategies;
import java.io.IOException;
import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author juan
 */
public class MatrixConfidenceTest {

    List<Call> calls = ofProject();
    DirectGraph dg = new DirectGraph();

    private List<Call> ofProject() {
        Project project = Project.root("")
                .path("src/test/java/br/edu/ifpb/gpes/mcp/samples/LambdaWithArguments.java")
                .sources("src/test/java/")
                .filter(".java");

        return Parse.with(ParseStrategies.JDT).from(project);
    }

    @Before
    public void before() throws IOException {
        calls.stream().filter((call) -> call.getCallMethod() != null).forEach(dg::adicionarNos);
    }

    @Test
    public void VertexNumberTest() throws IOException, InterruptedException, ClassNotFoundException {
        Assert.assertEquals(9, dg.getGraph().vertexSet().size());
    }
//
    @Test
    public void EdgeNumberTest() throws IOException {
        Assert.assertEquals(7, dg.getGraph().edgeSet().size());
    }

    @Test
    public void WeightSumTest() throws IOException {
        assertNotEquals(0, dg.generateMatrix().weightSum());
        dg.generateMatrix();

        assertEquals(8, dg.generateMatrix().weightSum());
        Assert.assertEquals(3, dg.generateMatrix().matrizDeAdjacencia().toArray().length);
    }

}
