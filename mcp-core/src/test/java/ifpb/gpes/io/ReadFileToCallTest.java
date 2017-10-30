package ifpb.gpes.io;

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

public class ReadFileToCallTest {

    private final List<Call> result = readOfFile();
    private static final Logger logger = Logger.getLogger(ReadFileToCallTest.class.getName());

    @Test
    public void caputreException(){
        
    }
    
    @Test
    public void testM1() {
        List<Call> expected = ofAllCall();

        assertThat(result, hasItems(Call.of("java.util.List", "add[ifpb.gpes.domain.HasJCFObject]", "boolean", "ifpb.gpes.domain.SampleObject", "m2[]", "void", null),
                Call.of("java.util.List", "remove[java.lang.Object]", "boolean", "ifpb.gpes.domain.SampleObject", "m3[]", "void", null),
                Call.of("ifpb.gpes.domain.HasJCFObject", "getElements[]", "java.util.List<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.SampleObject", "m3[]", "void", "isEmpty[]"),
                Call.of("java.util.List", "hashCode[]", "int", "ifpb.gpes.domain.AnonymousClass", "m1[]", "void", null),
                Call.of("ifpb.gpes.domain.HasJCFObject", "getElements[]", "java.util.List<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.AnonymousClass", "m1[]", "void", "hashCode[]")
        ));

        assertEquals(result.size(), expected.size());
        assertThat(result, is(expected));
        assertThat(result, IsIterableContainingInOrder.contains(expected.toArray()));

        result.forEach(no -> logger.log(Level.INFO, no.callGraph()));

    }

    private List<Call> ofAllCall() {
        return Arrays.asList(
                Call.of("java.util.List", "add[ifpb.gpes.domain.HasJCFObject]", "boolean", "ifpb.gpes.domain.SampleObject", "m2[]", "void",  null),
                Call.of("ifpb.gpes.domain.HasJCFObject", "getElements[]", "java.util.List<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.SampleObject", "m2[]", "void", "add[ifpb.gpes.domain.HasJCFObject]"),
                Call.of("java.util.List", "remove[java.lang.Object]", "boolean", "ifpb.gpes.domain.SampleObject", "m3[]", "void", null),
                Call.of("ifpb.gpes.domain.HasJCFObject", "getElements[]", "java.util.List<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.SampleObject", "m3[]", "void", "remove[java.lang.Object]"),
                Call.of("java.util.List", "isEmpty[]", "boolean", "ifpb.gpes.domain.SampleObject", "m3[]", "void", null),
                Call.of("ifpb.gpes.domain.HasJCFObject", "getElements[]", "java.util.List<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.SampleObject", "m3[]", "void", "isEmpty[]"),
                Call.of("java.util.List", "iterator[]", "java.util.Iterator<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.SampleObject", "m2[]", "void", null),
                Call.of("ifpb.gpes.domain.HasJCFObject", "getElements[]", "java.util.List<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.SampleObject", "m2[]", "void", "iterator[]"),
                Call.of("java.util.List", "listIterator[]", "java.util.ListIterator<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.SampleObject", "m2[]", "void", null),
                Call.of("ifpb.gpes.domain.HasJCFObject", "getElements[]", "java.util.List<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.SampleObject", "m2[]", "void", "listIterator[]"),
                Call.of("java.util.List", "iterator[]", "java.util.Iterator<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.Interface", "semRetorno[]", "void", null),
                Call.of("ifpb.gpes.domain.HasJCFObject", "getElements[]", "java.util.List<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.Interface", "semRetorno[]", "void", "iterator[]"),
                Call.of("java.util.List", "hashCode[]", "int", "ifpb.gpes.domain.AnonymousClass", "m1[]", "void", null),
                Call.of("ifpb.gpes.domain.HasJCFObject", "getElements[]", "java.util.List<ifpb.gpes.domain.HasJCFObject>", "ifpb.gpes.domain.AnonymousClass", "m1[]", "void", "hashCode[]"));

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
