package ifpb.gpes.jdt;

import ifpb.gpes.No;
import ifpb.gpes.Parse;
import ifpb.gpes.ParseStrategies;
import ifpb.gpes.Project;
import ifpb.gpes.SingletonPath;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class RandomCallsTest {

    private static final Logger logger = Logger.getLogger(RandomCallsTest.class.getName());
    private List<No> result = ofRandomCalls();

    @Test
    public void random() {
        assertThat(result, hasItems(
                No.of("ifpb.gpes.jdt.samples.I", "semRetorno[]", "void", "ifpb.gpes.jdt.samples.X",
                        "m3[]", null),
                No.of("ifpb.gpes.jdt.samples.X", "listar[java.util.List<ifpb.gpes.jdt.samples.A>]",
                        "void", "ifpb.gpes.jdt.samples.X", "m3[]", null),
                No.of("ifpb.gpes.jdt.samples.A", "getElements[]",
                        "java.util.List<ifpb.gpes.jdt.samples.A>", "ifpb.gpes.jdt.samples.X",
                        "m3[]", null),
                No.of("ifpb.gpes.jdt.samples.A", "m6[ifpb.gpes.jdt.samples.A]",
                        "java.util.function.Predicate<ifpb.gpes.jdt.samples.A>",
                        "java.util.function.Consumer<ifpb.gpes.jdt.samples.A>",
                        "accept[ifpb.gpes.jdt.samples.A]", "negate[]")
        ));
        assertNotNull(result);
        assertFalse(result.isEmpty());
        result.forEach(no -> logger.log(Level.INFO, no.callGraph()));

    }

    private List<No> ofRandomCalls() {
        Project project = Project
                .root(SingletonPath.ROOT)
                .path("src/test/java/ifpb/gpes/jdt/samples/X.java")
                .sources("src/test/java/")
                .filter(".java");

        return Parse.with(ParseStrategies.JDT).from(project);
    }

}
