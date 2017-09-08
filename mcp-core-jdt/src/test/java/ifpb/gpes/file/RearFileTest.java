package ifpb.gpes.file;

import ifpb.gpes.Call;
import ifpb.gpes.Parse;
import ifpb.gpes.ParseStrategy;
import ifpb.gpes.Project;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.hamcrest.CoreMatchers.*;
import org.hamcrest.collection.IsIterableContainingInOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class RearFileTest {

    private final List<Call> result = readOfFile();
    private static final Logger logger = Logger.getLogger(RearFileTest.class.getName());

    @Test
    public void testM1() {
        List<Call> expected = ofAllCall();

        assertThat(result, hasItems(Call.of("java.util.List", "add[br.edu.ifpb.gpes.mcp.samples.HasJCFObject]", "boolean", "br.edu.ifpb.gpes.mcp.samples.SampleObject", "m2[]", null),
                Call.of("java.util.List", "remove[java.lang.Object]", "boolean", "br.edu.ifpb.gpes.mcp.samples.SampleObject", "m3[]", null),
                Call.of("br.edu.ifpb.gpes.mcp.samples.HasJCFObject", "getElements[]", "java.util.List<br.edu.ifpb.gpes.mcp.samples.HasJCFObject>", "br.edu.ifpb.gpes.mcp.samples.SampleObject", "m3[]", "isEmpty[]"),
                Call.of("java.util.List", "hashCode[]", "int", "br.edu.ifpb.gpes.mcp.samples.AnonymousClass", "m1[]", null),
                Call.of("br.edu.ifpb.gpes.mcp.samples.HasJCFObject", "getElements[]", "java.util.List<br.edu.ifpb.gpes.mcp.samples.HasJCFObject>", "br.edu.ifpb.gpes.mcp.samples.AnonymousClass", "m1[]", "hashCode[]")
        ));

        assertEquals(result.size(), expected.size());
        assertThat(result, is(expected));
        assertThat(result, IsIterableContainingInOrder.contains(expected.toArray()));

        result.forEach(no -> logger.log(Level.INFO, no.callGraph()));

    }

    private List<Call> ofAllCall() {
        return Arrays.asList(
                Call.of("java.util.List", "add[br.edu.ifpb.gpes.mcp.samples.HasJCFObject]", "boolean", "br.edu.ifpb.gpes.mcp.samples.SampleObject", "m2[]", null),
                Call.of("br.edu.ifpb.gpes.mcp.samples.HasJCFObject", "getElements[]", "java.util.List<br.edu.ifpb.gpes.mcp.samples.HasJCFObject>", "br.edu.ifpb.gpes.mcp.samples.SampleObject", "m2[]", "add[br.edu.ifpb.gpes.mcp.samples.HasJCFObject]"),
                Call.of("java.util.List", "remove[java.lang.Object]", "boolean", "br.edu.ifpb.gpes.mcp.samples.SampleObject", "m3[]", null),
                Call.of("br.edu.ifpb.gpes.mcp.samples.HasJCFObject", "getElements[]", "java.util.List<br.edu.ifpb.gpes.mcp.samples.HasJCFObject>", "br.edu.ifpb.gpes.mcp.samples.SampleObject", "m3[]", "remove[java.lang.Object]"),
                Call.of("java.util.List", "isEmpty[]", "boolean", "br.edu.ifpb.gpes.mcp.samples.SampleObject", "m3[]", null),
                Call.of("br.edu.ifpb.gpes.mcp.samples.HasJCFObject", "getElements[]", "java.util.List<br.edu.ifpb.gpes.mcp.samples.HasJCFObject>", "br.edu.ifpb.gpes.mcp.samples.SampleObject", "m3[]", "isEmpty[]"),
                Call.of("java.util.List", "iterator[]", "java.util.Iterator<br.edu.ifpb.gpes.mcp.samples.HasJCFObject>", "br.edu.ifpb.gpes.mcp.samples.SampleObject", "m2[]", null),
                Call.of("br.edu.ifpb.gpes.mcp.samples.HasJCFObject", "getElements[]", "java.util.List<br.edu.ifpb.gpes.mcp.samples.HasJCFObject>", "br.edu.ifpb.gpes.mcp.samples.SampleObject", "m2[]", "iterator[]"),
                Call.of("java.util.List", "listIterator[]", "java.util.ListIterator<br.edu.ifpb.gpes.mcp.samples.HasJCFObject>", "br.edu.ifpb.gpes.mcp.samples.SampleObject", "m2[]", null),
                Call.of("br.edu.ifpb.gpes.mcp.samples.HasJCFObject", "getElements[]", "java.util.List<br.edu.ifpb.gpes.mcp.samples.HasJCFObject>", "br.edu.ifpb.gpes.mcp.samples.SampleObject", "m2[]", "listIterator[]"),
                Call.of("java.util.List", "iterator[]", "java.util.Iterator<br.edu.ifpb.gpes.mcp.samples.HasJCFObject>", "br.edu.ifpb.gpes.mcp.samples.Interface", "semRetorno[]", null),
                Call.of("br.edu.ifpb.gpes.mcp.samples.HasJCFObject", "getElements[]", "java.util.List<br.edu.ifpb.gpes.mcp.samples.HasJCFObject>", "br.edu.ifpb.gpes.mcp.samples.Interface", "semRetorno[]", "iterator[]"),
                Call.of("java.util.List", "hashCode[]", "int", "br.edu.ifpb.gpes.mcp.samples.AnonymousClass", "m1[]", null),
                Call.of("br.edu.ifpb.gpes.mcp.samples.HasJCFObject", "getElements[]", "java.util.List<br.edu.ifpb.gpes.mcp.samples.HasJCFObject>", "br.edu.ifpb.gpes.mcp.samples.AnonymousClass", "m1[]", "hashCode[]"));

    }

    private List<Call> readOfFile() {
        Project project = Project
                .root("")
                .path("src/test/resources/client.properties")
                .filter(".properties");

        return Parse.with(new FileStrategy()).from(project);

    }

    private static class FileStrategy implements ParseStrategy {

        @Override
        public List<Call> from(Project project) {
            return project.files()
                    .map(this::toListCall)
                    .flatMap(x -> x.stream())
                    .collect(Collectors.toList());
        }

        private List<Call> toListCall(Path t) {
            //                        try {
//                            return Files.lines(t)
//                                    .map(Call::of)
//                                    .collect(Collectors.toList());
//                        } catch (IOException ex) {
//                            return Collections.<Call>emptyList();
//                        }
            try (Stream<String> stream = Files.lines(t)) {
                return stream
                        .map(Call::of)
                        .collect(Collectors.toList());
            } catch (IOException ex) {
                return Collections.<Call>emptyList();
            }
        }
    }

}
