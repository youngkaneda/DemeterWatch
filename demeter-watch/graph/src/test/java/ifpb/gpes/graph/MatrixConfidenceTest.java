package ifpb.gpes.graph;

import ifpb.gpes.Call;
import ifpb.gpes.Parse;
import ifpb.gpes.Project;
import ifpb.gpes.io.PathUtils;
import ifpb.gpes.jdt.ParseStrategies;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class MatrixConfidenceTest {

    private final List<Call> calls = ofProject();
    private final DefaultDirectGraph dg = new DefaultDirectGraph();
    private static final String sources = PathUtils.connect("..", "samples", "src", "main", "java");

    private List<Call> ofProject() {
        Project project = Project
            .root("")
            .path(sources + File.separator + PathUtils.connect("ifpb","gpes","domain","LambdaWithArguments.java"))
            .sources(sources)
            .filter(".java");

        return Parse.with(ParseStrategies.JDT).from(project);
    }

    @Before
    public void before() {
        calls.stream().forEach(dg::buildNode);
    }

    @Test
    public void VertexNumberTest() {
        Assert.assertEquals(21, dg.vertex().size());
    }

    @Test
    public void WeightSumTest() throws IOException {
        assertNotEquals(0, dg.toMatrix().sumAllWeight());
        assertEquals(17, dg.toMatrix().sumAllWeight());
        Assert.assertEquals(13, dg.toMatrix().adjacencyMatrix().toArray().length);
    }

}