package ifpb.gpes.jdt;

import ifpb.gpes.Call;
import ifpb.gpes.Parse;
import ifpb.gpes.Project;
import ifpb.gpes.SingletonPath;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import org.junit.Test;

public class MethodReferenceTest {

    private final List<Call> result = ofMethodReferenceClass();
    private static final Logger logger = Logger.getLogger(MethodReferenceTest.class.getName());

    @Test
    public void testeChamadaComReference() {
    }

    @Test
    public void testM1() {
        List<Call> expected = ofListM1();
        result.forEach(no -> System.out.println(no.callGraph()));
    }

    private List<Call> ofListM1() {
        return Arrays.asList();
    }

    private List<Call> ofMethodReferenceClass() {
        Project project = Project
                .root(SingletonPath.ROOT)
                .path("/home/juan/facul/periodo4/projetoDePesquisa/mcp/"
                        + "mcp-core-jdt/src/test/java/ifpb/gpes/jdt/samples/"
                        + "MethodReferenceExample.java") // root
                .sources("src/test/java/") // root - não obrigatorio
                .filter(".java");

        return Parse.with(ParseStrategies.JDT).from(project);

    }

}
