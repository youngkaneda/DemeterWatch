package ifpb.gpes.jdt;

import ifpb.gpes.Call;
import ifpb.gpes.Parse;
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
    private List<Call> result = ofRandomCalls();

    @Test
    public void random() {
        assertThat(result, hasItems(Call.of("ifpb.gpes.jdt.samples.Interface", "semRetorno[]", "void", "ifpb.gpes.jdt.samples.LambdaAndAnonymous",
                        "m3[]", null),
                Call.of("ifpb.gpes.jdt.samples.LambdaAndAnonymous", "listar[java.util.List<ifpb.gpes.jdt.samples.HasJCFObject>]",
                        "void", "ifpb.gpes.jdt.samples.LambdaAndAnonymous", "m3[]", null),
                Call.of("ifpb.gpes.jdt.samples.HasJCFObject", "getElements[]",
                        "java.util.List<ifpb.gpes.jdt.samples.HasJCFObject>", "ifpb.gpes.jdt.samples.LambdaAndAnonymous",
                        "m3[]", null),
                Call.of("ifpb.gpes.jdt.samples.HasJCFObject", "m6[ifpb.gpes.jdt.samples.HasJCFObject]",
                        "java.util.function.Predicate<ifpb.gpes.jdt.samples.HasJCFObject>",
                        "java.util.function.Consumer<ifpb.gpes.jdt.samples.HasJCFObject>",
                        "accept[ifpb.gpes.jdt.samples.HasJCFObject]", "negate[]")
        ));
        assertNotNull(result);
        assertFalse(result.isEmpty());
        result.forEach(no -> logger.log(Level.INFO, no.callGraph()));

    }

    private List<Call> ofRandomCalls() {
        Project project = Project
                .root(SingletonPath.ROOT)
                .path("src/test/java/ifpb/gpes/jdt/samples/LambdaAndAnonymous.java")
                .sources("src/test/java/")
                .filter(".java");

        return Parse.with(ParseStrategies.JDT).from(project);
    }

}
