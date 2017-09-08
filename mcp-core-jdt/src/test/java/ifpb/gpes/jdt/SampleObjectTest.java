package ifpb.gpes.jdt;

import ifpb.gpes.Call;
import ifpb.gpes.Parse;
import ifpb.gpes.Project;
import java.util.List;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author juan
 */
public class SampleObjectTest {

    private static List<Call> calls() {
        Project project = Project.root("")
                .path("/home/juan/facul/periodo4/projetoDePesquisa/mcp/"
                        + "mcp-core-jdt/src/test/java/ifpb/gpes/jdt/samples/"
                + "SampleObject.java")
                .sources("src/test/java/")
                .filter(".java");

        return Parse.with(ParseStrategies.JDT).from(project);
    }

    @Test
    public void sampleTest() {

        List<Call> samples = calls();

        assertThat(samples, hasItems(Call.of("ifpb.gpes.jdt.samples.HasJCFObject", "getElements[]", "java.util.List<ifpb.gpes.jdt.samples.HasJCFObject>", "ifpb.gpes.jdt.samples.SampleObject", "teste[]", "add[ifpb.gpes.jdt.samples.HasJCFObject]"),
                Call.of("java.util.List", "add[ifpb.gpes.jdt.samples.HasJCFObject]", "boolean", "ifpb.gpes.jdt.samples.SampleObject", "teste[]", null)));

        assertEquals(2,samples.size());
    }

}
