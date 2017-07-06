package ifpb.gpes.jdt;

import ifpb.gpes.No;
import ifpb.gpes.io.SmartFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class LambdaArgumentsTest {

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
    }

    private List<No> ofLambdaArguments() {
        SmartAllVisitor visitor = new SmartAllVisitor();

//        String path = "/Users/job/Documents/dev/gpes/parse-review/parse-jdt/src/test/java/ifpb/gpes/jdt/samples/LambdaComArgumento.java";
//        String[] sources = {"/Users/job/Documents/dev/gpes/parse-review/parse-jdt/src/test/java/"};
        String path = "/home/juan/facul/periodo4/projetoDePesquisa/parse-review/parse-jdt/src/test/java/ifpb/gpes/jdt/samples/LambdaComArgumento.java";
        String[] sources = {"/home/juan/facul/periodo4/projetoDePesquisa/parse-review/parse-jdt/src/test/java"};

        SmartFile smart = SmartFile.from(Paths.get(path));
        SmartASTParser parser = SmartASTParser.from(sources);

        Stream<Path> files = smart.extension(".java");

        files.forEach(p -> {
            parser.updateUnitName(p);
            parser.acceptVisitor(visitor);
        });
        return visitor.methodsCall();
    }

}
