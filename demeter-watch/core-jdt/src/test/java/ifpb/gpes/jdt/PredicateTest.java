package ifpb.gpes.jdt;

import ifpb.gpes.Call;
import ifpb.gpes.Parse;
import ifpb.gpes.Project;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.hasItems;

import ifpb.gpes.io.PathUtils;
import org.junit.Assert;

import static org.junit.Assert.assertThat;

import org.junit.Test;

public class PredicateTest {

    private static final String sources = PathUtils.connect("..", "samples", "src", "main", "java");

    @Test
    public void returnSubListTest() {
        Predicate<Call> predicate = DecoratorPredicate.and(new TypePredicate("ifpb.gpes.domain.HasJCFObject"));
        List<Call> collect = calls().stream().filter(predicate).collect(Collectors.toList());
        Assert.assertEquals(collect.size(), 7);
        assertThat(collect, hasItems(
            Call.of("ifpb.gpes.domain.HasJCFObject", "getElements[]", "java.util.List<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.LambdaWithArguments", "m1[]", "void", "forEach[java.util.function.Consumer<? super ifpb.gpes.domain.HasJCFObject>]", "new HasJCFObject()"),
            Call.of("ifpb.gpes.domain.HasJCFObject", "getElements[]", "java.util.List<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.LambdaWithArguments", "m2[]", "void", "forEach[java.util.function.Consumer<? super ifpb.gpes.domain.HasJCFObject>]", "new HasJCFObject()"),
            Call.of("ifpb.gpes.domain.HasJCFObject", "getElements[]", "java.util.List<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.LambdaWithArguments", "m3[]", "void", "toArray[]", "a"),
            Call.of("ifpb.gpes.domain.HasJCFObject", "getElements[]", "java.util.List<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.LambdaWithArguments", "m3[]", "void", "stream[]", "a"),
            Call.of("ifpb.gpes.domain.HasJCFObject", "getElements[]", "java.util.List<ifpb.gpes.domain.HasJCFObject>", "java.util.function.Consumer<? super ifpb.gpes.domain.HasJCFObject>", "accept[? super ifpb.gpes.domain.HasJCFObject]", "void", "add[ifpb.gpes.domain.HasJCFObject]", "t")
        ));
    }

    @Test
    public void listPredicate() {
        List<Predicate<Call>> predicates = Arrays.asList(new MethodPredicate("add"), new TypePredicate("ifpb.gpes.domain.HasJCFObject"));
        Predicate<Call> compositePredicate = predicates.stream().reduce(w -> true, Predicate::and);
        List<Call> collect = calls().stream().filter(compositePredicate).toList();
        collect.forEach(System.out::println);
    }

    @Test
    public void andPredicate() {
        Predicate<Call> predicate = new DecoratorPredicate()
            .and(new TypePredicate("java.util.List"))
            .or(new TypePredicate("java.util.Collection"));
        List<Call> collect = calls().stream().filter(predicate).toList();
        collect.forEach(System.out::println);
    }

    @Test
    public void decoratorPredicate() {
        Predicate<Call> compositePredicate = DecoratorPredicate.and(new MethodPredicate("forEach"), new TypePredicate("java.util.Collection"));
        List<Call> collect = calls().stream()
            .filter(compositePredicate).toList();
        collect.forEach(System.out::println);
    }

    private static List<Call> calls() {
        Project project = Project
            .root("")
            .path(sources + File.separator + PathUtils.connect("ifpb", "gpes", "domain", "LambdaWithArguments.java"))
            .sources(sources)
            .filter(".java");

        return Parse.with(ParseStrategies.JDT).from(project);
    }

}

class MethodPredicate implements Predicate<Call> {

    private final String method;

    public MethodPredicate(String method) {
        this.method = method;
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
        return compositePredicate;
    }
}
