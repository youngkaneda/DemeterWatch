package ifpb.gpes.graph;

import ifpb.gpes.Call;
import ifpb.gpes.Parse;
import ifpb.gpes.Project;
import ifpb.gpes.jdt.ParseStrategies;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import static org.hamcrest.CoreMatchers.hasItems;
import org.junit.Assert;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 17/08/2017, 15:32:22
 */
public class PredicateTest {

    @Test
    public void ReturnSubListTest() {
//        Predicate<Call> and = DecoratorPredicate.and(new TypePredicate("java.util.Collection"));
        Predicate<Call> and = DecoratorPredicate.and(new TypePredicate("java.util.List"));
        List<Call> coletado = calls().stream().filter(and).collect(Collectors.toList());
        calls().forEach(System.out::println);
        Assert.assertEquals(coletado.size(), 5);
//        Assert.assertEquals(coletado.size(), 6);

        assertThat(coletado, hasItems(
                Call.of("br.edu.ifpb.gpes.mcp.samples.HasJCFObject", "getElements[]", "java.util.List<br.edu.ifpb.gpes.mcp.samples.HasJCFObject>", "br.edu.ifpb.gpes.mcp.samples.LambdaWithArguments", "m1[]", "forEach[java.util.function.Consumer<? super br.edu.ifpb.gpes.mcp.samples.HasJCFObject>]"),
                Call.of("br.edu.ifpb.gpes.mcp.samples.HasJCFObject", "getElements[]", "java.util.List<br.edu.ifpb.gpes.mcp.samples.HasJCFObject>", "br.edu.ifpb.gpes.mcp.samples.LambdaWithArguments", "m2[]", "forEach[java.util.function.Consumer<? super br.edu.ifpb.gpes.mcp.samples.HasJCFObject>]"),
                Call.of("br.edu.ifpb.gpes.mcp.samples.HasJCFObject", "getElements[]", "java.util.List<br.edu.ifpb.gpes.mcp.samples.HasJCFObject>", "br.edu.ifpb.gpes.mcp.samples.LambdaWithArguments", "m3[]", "toArray[]"),
                Call.of("br.edu.ifpb.gpes.mcp.samples.HasJCFObject", "getElements[]", "java.util.List<br.edu.ifpb.gpes.mcp.samples.HasJCFObject>", "br.edu.ifpb.gpes.mcp.samples.LambdaWithArguments", "m3[]", "stream[]"),
                Call.of("br.edu.ifpb.gpes.mcp.samples.HasJCFObject", "getElements[]", "java.util.List<br.edu.ifpb.gpes.mcp.samples.HasJCFObject>", "java.util.function.Consumer<? super br.edu.ifpb.gpes.mcp.samples.HasJCFObject>", "accept[? super br.edu.ifpb.gpes.mcp.samples.HasJCFObject]", "add[br.edu.ifpb.gpes.mcp.samples.HasJCFObject]")
//                Call.of("br.edu.ifpb.gpes.mcp.samples.LambdaWithArguments", "m4[]", "java.util.Set<java.lang.Integer>", "br.edu.ifpb.gpes.mcp.samples.LambdaWithArguments", "m1[]", "add[java.lang.Integer]")
            )
        );

    }

    @Test
    public void listPredicate() {
        List<Predicate<Call>> predicates = Arrays
                .asList(new MethodPredicate("accept"), new TypePredicate("java.util.List"));
        Predicate<Call> compositePredicate
                = predicates.stream().reduce(w -> true, Predicate::and);

        List<Call> collect = calls().stream().filter(compositePredicate).collect(Collectors.toList());

    }

    @Test
    public void andPredicate() {
        Predicate<Call> and = new DecoratorPredicate()
                .and(new TypePredicate("java.util.List"))
                .or(new TypePredicate("java.util.Collection"))
                .and(new MethodPredicate("add"));

//        
        List<Call> collect = calls().stream().filter(and).collect(Collectors.toList());

    }

    @Test
    public void decoratorPredicate() {
        Predicate<Call> compositePredicate = DecoratorPredicate
                .and(new MethodPredicate("accept"),
                        new TypePredicate("java.util.List")
                        );

        List<Call> collect = calls().stream().filter(compositePredicate).collect(Collectors.toList());

    }

    private static List<Call> calls() {
//        return Arrays.asList(
//                Call.of("br.edu.ifpb.gpes.mcp.samples.HasJCFObject", "getElements[]", "java.util.List<br.edu.ifpb.gpes.mcp.samples.HasJCFObject>", "br.edu.ifpb.gpes.mcp.samples.LambdaWithArguments", "m1[]", "forEach[java.util.function.Consumer<? super br.edu.ifpb.gpes.mcp.samples.HasJCFObject>]"),
//                Call.of("java.util.function.Predicate", "negate[]", "java.util.function.Predicate<br.edu.ifpb.gpes.mcp.samples.HasJCFObject>", "java.lang.Object", "accept[br.edu.ifpb.gpes.mcp.samples.HasJCFObject]", null));

        Project project = Project
                .root("")
                .path("/home/juan/facul/periodo4/projetoDePesquisa/mcp/"
                        + "mcp-core-jdt/src/test/java/ifpb/gpes/jdt/samples/"
                        + "LambdaWithArguments.java") // root
                .sources("src/test/java/") // root - não obrigatorio
                .filter(".java");

        return Parse.with(ParseStrategies.JDT).from(project);
    }

}

class MethodPredicate implements Predicate<Call> {

    private final String method;

    public MethodPredicate(String add) {
        this.method = add;
    }

    @Override
    public boolean test(Call t) {
        String retorno = t.getCallMethod();
        return retorno == null ? false : t.getCallMethod().contains(method);
    }
}

class TypePredicate implements Predicate<Call> {

    private String value;

    public TypePredicate(String value) {
        this.value = value;
    }

    @Override
    public boolean test(Call t) {
        return t.isFrom(value);
    }

}

class DecoratorPredicate<T> {

    public static <T> Predicate<T> and(Predicate<T>... predicates) {
        Predicate<T> compositePredicate
                = Arrays.asList(predicates).stream().reduce(c -> true, Predicate::and);
//        Arrays.asList(predicates).stream().reduce(Predicate::and).orElse(x->true);
//        Arrays.asList(predicates).stream().reduce(Predicate::or).orElse(x->false);
        return compositePredicate;
    }

}
