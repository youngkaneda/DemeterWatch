package ifpb.gpes.graph;

import ifpb.gpes.Call;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class ConvertListCallsToMatrixTest {

    private List<Call> calls;

    @Before
    public void before() {
        this.calls = Arrays.asList(
            Call.of("java.util.List", "add[ifpb.gpes.domain.HasJCFObject]", "boolean", "ifpb.gpes.domain.SampleObject", "test[]", "void", null, "a.getElements()"),
            Call.of("ifpb.gpes.domain.HasJCFObject", "getElements[]", "java.util.List<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.SampleObject", "test[]", "void", "add[ifpb.gpes.domain.HasJCFObject]", "a"));
    }

    @Test
    public void collectTest() {

        Graph graph = calls.stream().collect(
            DefaultDirectGraph::new,
            DefaultDirectGraph::buildNode,
            (DefaultDirectGraph t, DefaultDirectGraph u) -> {}
        );

        Matrix generateMatrix = graph.toMatrix();

        assertNotNull(graph);
        assertNotNull(generateMatrix);
        assertEquals(generateMatrix.sumAllWeight(), 2);
    }

    @Test
    public void reduceTest() {

        Graph graph = calls.stream().reduce(new DefaultDirectGraph(),
            (DefaultDirectGraph t, Call u) -> {
                t.buildNode(u);
                return t;
            }, (DefaultDirectGraph t, DefaultDirectGraph u) -> t);

        Matrix matrix = graph.toMatrix();

        assertNotNull(graph);
        assertNotNull(matrix);
        assertEquals(matrix.sumAllWeight(), 2);
    }

    @Test
    public void adapterTest() {

        Graph graph = new AdapterGraph().apply(calls);

        Matrix generateMatrix = graph.toMatrix();

        assertNotNull(graph);
        assertNotNull(generateMatrix);
        assertEquals(generateMatrix.sumAllWeight(), 2);
    }

}
