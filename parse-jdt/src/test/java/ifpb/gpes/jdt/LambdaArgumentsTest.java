package ifpb.gpes.jdt;

import ifpb.gpes.No;
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
    private final List<No> result = ofLambdaArguments();

    @Test
    public void testeM1() {
        assertThat(result, hasItems(
                No.of("java.lang.Iterable", "forEach[java.util.function.Consumer<? super ifpb.gpes.jdt.samples.A>]", "void", "ifpb.gpes.jdt.samples.LambdaComArgumento", "m1[]", null),
                No.of("ifpb.gpes.jdt.samples.A", "getElements[]", "java.util.List<ifpb.gpes.jdt.samples.A>", "ifpb.gpes.jdt.samples.LambdaComArgumento", "m1[]", "forEach[java.util.function.Consumer<? super ifpb.gpes.jdt.samples.A>]"),
                No.of("java.util.function.Predicate", "negate[]", "java.util.function.Predicate<ifpb.gpes.jdt.samples.A>", "java.lang.Object", "accept[ifpb.gpes.jdt.samples.A]", null),
                No.of("ifpb.gpes.jdt.samples.A", "m6[ifpb.gpes.jdt.samples.A]", "java.util.function.Predicate<ifpb.gpes.jdt.samples.A>", "java.lang.Object", "accept[ifpb.gpes.jdt.samples.A]", "negate[]"),
                No.of("java.lang.Iterable", "forEach[java.util.function.Consumer<? super ifpb.gpes.jdt.samples.A>]", "void", "ifpb.gpes.jdt.samples.LambdaComArgumento", "m2[]", null),
                No.of("ifpb.gpes.jdt.samples.A", "getElements[]", "java.util.List<ifpb.gpes.jdt.samples.A>", "ifpb.gpes.jdt.samples.LambdaComArgumento", "m2[]", "forEach[java.util.function.Consumer<? super ifpb.gpes.jdt.samples.A>]"),
                No.of("java.util.function.Predicate", "negate[]", "java.util.function.Predicate<ifpb.gpes.jdt.samples.A>", "java.util.function.Consumer<ifpb.gpes.jdt.samples.A>", "accept[ifpb.gpes.jdt.samples.A]", null),
                No.of("ifpb.gpes.jdt.samples.A", "m6[ifpb.gpes.jdt.samples.A]", "java.util.function.Predicate<ifpb.gpes.jdt.samples.A>", "java.util.function.Consumer<ifpb.gpes.jdt.samples.A>", "accept[ifpb.gpes.jdt.samples.A]", "negate[]"),
                No.of("java.util.stream.Stream", "forEach[java.util.function.Consumer<? super ifpb.gpes.jdt.samples.A>]", "void", "ifpb.gpes.jdt.samples.LambdaComArgumento", "m3[]", null),
                No.of("java.util.Collection", "stream[]", "java.util.stream.Stream<ifpb.gpes.jdt.samples.A>", "ifpb.gpes.jdt.samples.LambdaComArgumento", "m3[]", "forEach[java.util.function.Consumer<? super ifpb.gpes.jdt.samples.A>]")
        ));
        assertNotNull(result);
        assertFalse(result.isEmpty());
        result.forEach(no -> logger.log(Level.INFO, no.callGraph()));
    }

    private List<No> ofLambdaArguments() {
        Project project = Project
                .root(SingletonPath.ROOT)
                .path("src/test/java/ifpb/gpes/jdt/samples/LambdaComArgumento.java") // root
                .sources("src/test/java/") // root - não obrigatorio
                .filter(".java");

        return Parse.with(ParseStrategies.JDT).from(project);
    }

}
