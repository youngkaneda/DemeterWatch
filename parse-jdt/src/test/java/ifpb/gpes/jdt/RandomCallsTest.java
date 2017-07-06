package ifpb.gpes.jdt;

import ifpb.gpes.No;
import ifpb.gpes.Parse;
import ifpb.gpes.Project;
import java.util.List;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class RandomCallsTest {

    List<No> result = ofRandomCalls();

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
    }

    private List<No> ofRandomCalls() {
        Project project = Project
                .root("/home/juan/facul/periodo4/projetoDePesquisa/parse-review/parse-jdt/")
                .path("src/test/java/ifpb/gpes/jdt/samples/X.java")
                .sources("src/test/java/")
                .filter(".java");

        return Parse.with(Parse.ParseStrategies.JDT).from(project);
    }

}
