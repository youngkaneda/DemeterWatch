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

public class LambdaArgumentsTest {

    private static final Logger logger = Logger.getLogger(LambdaArgumentsTest.class.getName());
    private final List<Call> result = ofLambdaArguments();

    @Test
    public void testeM1() {
        assertThat(result, hasItems(Call.of("java.lang.Iterable", "forEach[java.util.function.Consumer<? super ifpb.gpes.jdt.samples.HasJCFObject>]", "void", "ifpb.gpes.jdt.samples.LambdaWithArguments", "m1[]", null),
                Call.of("ifpb.gpes.jdt.samples.HasJCFObject", "getElements[]", "java.util.List<ifpb.gpes.jdt.samples.HasJCFObject>", "ifpb.gpes.jdt.samples.LambdaWithArguments", "m1[]", "forEach[java.util.function.Consumer<? super ifpb.gpes.jdt.samples.HasJCFObject>]"),
                Call.of("java.util.function.Predicate", "negate[]", "java.util.function.Predicate<ifpb.gpes.jdt.samples.HasJCFObject>", "java.lang.Object", "accept[ifpb.gpes.jdt.samples.HasJCFObject]", null),
                Call.of("ifpb.gpes.jdt.samples.HasJCFObject", "m6[ifpb.gpes.jdt.samples.HasJCFObject]", "java.util.function.Predicate<ifpb.gpes.jdt.samples.HasJCFObject>", "java.lang.Object", "accept[ifpb.gpes.jdt.samples.HasJCFObject]", "negate[]"),
                Call.of("java.lang.Iterable", "forEach[java.util.function.Consumer<? super ifpb.gpes.jdt.samples.HasJCFObject>]", "void", "ifpb.gpes.jdt.samples.LambdaWithArguments", "m2[]", null),
                Call.of("ifpb.gpes.jdt.samples.HasJCFObject", "getElements[]", "java.util.List<ifpb.gpes.jdt.samples.HasJCFObject>", "ifpb.gpes.jdt.samples.LambdaWithArguments", "m2[]", "forEach[java.util.function.Consumer<? super ifpb.gpes.jdt.samples.HasJCFObject>]"),
                Call.of("java.util.function.Predicate", "negate[]", "java.util.function.Predicate<ifpb.gpes.jdt.samples.HasJCFObject>", "java.util.function.Consumer<ifpb.gpes.jdt.samples.HasJCFObject>", "accept[ifpb.gpes.jdt.samples.HasJCFObject]", null),
                Call.of("ifpb.gpes.jdt.samples.HasJCFObject", "m6[ifpb.gpes.jdt.samples.HasJCFObject]", "java.util.function.Predicate<ifpb.gpes.jdt.samples.HasJCFObject>", "java.util.function.Consumer<ifpb.gpes.jdt.samples.HasJCFObject>", "accept[ifpb.gpes.jdt.samples.HasJCFObject]", "negate[]"),
                Call.of("java.util.stream.Stream", "forEach[java.util.function.Consumer<? super ifpb.gpes.jdt.samples.HasJCFObject>]", "void", "ifpb.gpes.jdt.samples.LambdaWithArguments", "m3[]", null),
                Call.of("java.util.Collection", "stream[]", "java.util.stream.Stream<ifpb.gpes.jdt.samples.HasJCFObject>", "ifpb.gpes.jdt.samples.LambdaWithArguments", "m3[]", "forEach[java.util.function.Consumer<? super ifpb.gpes.jdt.samples.HasJCFObject>]")
        ));
        assertNotNull(result);
        assertFalse(result.isEmpty());
        result.forEach(no -> logger.log(Level.INFO, no.callGraph()));
    }

    private List<Call> ofLambdaArguments() {
        Project project = Project
                .root(SingletonPath.ROOT)
                .path("src/test/java/ifpb/gpes/jdt/samples/LambdaWithArguments.java") // root
                .sources("src/test/java/") // root - não obrigatorio
                .filter(".java");

        return Parse.with(ParseStrategies.JDT).from(project);
    }

}
