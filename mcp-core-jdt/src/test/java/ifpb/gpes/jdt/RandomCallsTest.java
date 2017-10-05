package ifpb.gpes.jdt;

import ifpb.gpes.Call;
import ifpb.gpes.Parse;
import ifpb.gpes.Project;
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
    private static final String sources = "../mcp-samples/src/main/java/";

    @Test
    public void random() {
        assertThat(result, hasItems(Call.of("ifpb.gpes.domain.Interface", "semRetorno[]", "void", "ifpb.gpes.domain.LambdaAndAnonymous",
                "m3[]", null),
                Call.of("ifpb.gpes.domain.LambdaAndAnonymous", "listar[java.util.List<ifpb.gpes.domain.HasJCFObject>]",
                        "void", "ifpb.gpes.domain.LambdaAndAnonymous", "m3[]", null),
                Call.of("ifpb.gpes.domain.HasJCFObject", "getElements[]",
                        "java.util.List<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.LambdaAndAnonymous",
                        "m3[]", null),
                Call.of("ifpb.gpes.domain.HasJCFObject", "m6[ifpb.gpes.domain.HasJCFObject]",
                        "java.util.function.Predicate<ifpb.gpes.domain.HasJCFObject>",
                        "java.util.function.Consumer<ifpb.gpes.domain.HasJCFObject>",
                        "accept[ifpb.gpes.domain.HasJCFObject]", "negate[]")
        ));
        assertNotNull(result);
        assertFalse(result.isEmpty());
        result.forEach(no -> logger.log(Level.INFO, no.callGraph()));

    }

    private List<Call> ofRandomCalls() {
        Project project = Project
                .root("")
                .path(sources + "ifpb/gpes/domain/LambdaAndAnonymous.java")
                .sources(sources)
                .filter(".java");

        return Parse.with(ParseStrategies.JDT).from(project);
    }

}
