package ifpb.gpes.jdt;

import ifpb.gpes.Call;
import ifpb.gpes.Parse;
import ifpb.gpes.Project;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.hamcrest.CoreMatchers.*;
import org.hamcrest.collection.IsIterableContainingInOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class AnonymousClassTest {

    private final List<Call> result = ofAnonymousClass();
    private static final Logger logger = Logger.getLogger(AnonymousClassTest.class.getName());
    private static final String sources = "../mcp-samples/src/main/java/";

    @Test
    public void testM1() {
        List<Call> expected = ofListM1();

        assertThat(result, hasItems(Call.of("java.util.List", "add[ifpb.gpes.domain.HasJCFObject]", "boolean", "ifpb.gpes.domain.SampleObject", "m2[]", null),
                Call.of("java.util.List", "remove[java.lang.Object]", "boolean", "ifpb.gpes.domain.SampleObject", "m3[]", null),
                Call.of("ifpb.gpes.domain.HasJCFObject", "getElements[]", "java.util.List<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.SampleObject", "m3[]", "isEmpty[]"),
                Call.of("java.util.List", "hashCode[]", "int", "ifpb.gpes.domain.AnonymousClass", "m1[]", null),
                Call.of("ifpb.gpes.domain.HasJCFObject", "getElements[]", "java.util.List<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.AnonymousClass", "m1[]", "hashCode[]")
        ));

        assertEquals(result.size(), expected.size());
        assertThat(result, is(expected));
        assertThat(result, IsIterableContainingInOrder.contains(expected.toArray()));

        result.forEach(no -> logger.log(Level.INFO, no.callGraph()));

    }

    private List<Call> ofListM1() {
        return Arrays.asList(Call.of("java.util.List", "add[ifpb.gpes.domain.HasJCFObject]", "boolean", "ifpb.gpes.domain.SampleObject", "m2[]", null),
                Call.of("ifpb.gpes.domain.HasJCFObject", "getElements[]", "java.util.List<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.SampleObject", "m2[]", "add[ifpb.gpes.domain.HasJCFObject]"),
                Call.of("java.util.List", "remove[java.lang.Object]", "boolean", "ifpb.gpes.domain.SampleObject", "m3[]", null),
                Call.of("ifpb.gpes.domain.HasJCFObject", "getElements[]", "java.util.List<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.SampleObject", "m3[]", "remove[java.lang.Object]"),
                Call.of("java.util.List", "isEmpty[]", "boolean", "ifpb.gpes.domain.SampleObject", "m3[]", null),
                Call.of("ifpb.gpes.domain.HasJCFObject", "getElements[]", "java.util.List<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.SampleObject", "m3[]", "isEmpty[]"),
                Call.of("java.util.List", "iterator[]", "java.util.Iterator<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.SampleObject", "m2[]", null),
                Call.of("ifpb.gpes.domain.HasJCFObject", "getElements[]", "java.util.List<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.SampleObject", "m2[]", "iterator[]"),
                Call.of("java.util.List", "listIterator[]", "java.util.ListIterator<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.SampleObject", "m2[]", null),
                Call.of("ifpb.gpes.domain.HasJCFObject", "getElements[]", "java.util.List<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.SampleObject", "m2[]", "listIterator[]"),
                Call.of("java.util.List", "iterator[]", "java.util.Iterator<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.Interface", "semRetorno[]", null),
                Call.of("ifpb.gpes.domain.HasJCFObject", "getElements[]", "java.util.List<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.Interface", "semRetorno[]", "iterator[]"),
                Call.of("java.util.List", "hashCode[]", "int", "ifpb.gpes.domain.AnonymousClass", "m1[]", null),
                Call.of("ifpb.gpes.domain.HasJCFObject", "getElements[]", "java.util.List<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.AnonymousClass", "m1[]", "hashCode[]"));

    }

    private List<Call> ofAnonymousClass() {
        Project project = Project
                .root("")
                .path(sources + "ifpb/gpes/domain/AnonymousClass.java") // root
                .sources(sources) // root - não obrigatorio
                .filter(".java");

        return Parse.with(ParseStrategies.JDT).from(project);

    }

}
