package ifpb.gpes.jdt;

import ifpb.gpes.No;
import ifpb.gpes.Parse;
import ifpb.gpes.Project;
import ifpb.gpes.SingletonPath;
import ifpb.gpes.jdt.ParseStrategies;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import org.junit.Test;

public class MethodReferenceTest {

    private final List<No> result = ofMethodReferenceClass();
    private static final Logger logger = Logger.getLogger(MethodReferenceTest.class.getName());

    @Test
    public void testeChamadaComReference() {
    }

    @Test
    public void testM1() {
        List<No> expected = ofListM1();
        result.forEach(no -> System.out.println(no.callGraph()));
    }

    private List<No> ofListM1() {
        return Arrays.asList();
    }

    private List<No> ofMethodReferenceClass() {
        Project project = Project
                .root(SingletonPath.ROOT)
                .path("src/test/java/ifpb/gpes/jdt/samples/ExemploComMethodReference.java") // root
                .sources("src/test/java/") // root - não obrigatorio
                .filter(".java");

        return Parse.with(ParseStrategies.JDT).from(project);

    }

}
