/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.gpes.jdt;

import ifpb.gpes.No;
import ifpb.gpes.Parse;
import ifpb.gpes.Project;
import ifpb.gpes.io.SmartFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.*;
import org.hamcrest.collection.IsIterableContainingInOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class AnonymousClassTest {

    private final List<No> result = ofAnonymousClass();

    @Test
    public void testM1() {
        List<No> expected = ofListM1();

        assertThat(result, hasItems(
                No.of("java.util.List", "add[ifpb.gpes.jdt.samples.A]", "boolean", "ifpb.gpes.jdt.samples.D", "m2[]", null),
                No.of("java.util.List", "remove[java.lang.Object]", "boolean", "ifpb.gpes.jdt.samples.D", "m3[]", null),
                No.of("ifpb.gpes.jdt.samples.A", "getElements[]", "java.util.List<ifpb.gpes.jdt.samples.A>", "ifpb.gpes.jdt.samples.D", "m3[]", "isEmpty[]"),
                No.of("java.util.List", "hashCode[]", "int", "ifpb.gpes.jdt.samples.ClasseAnonima", "m1[]", null),
                No.of("ifpb.gpes.jdt.samples.A", "getElements[]", "java.util.List<ifpb.gpes.jdt.samples.A>", "ifpb.gpes.jdt.samples.ClasseAnonima", "m1[]", "hashCode[]")
        ));

        assertEquals(result.size(), expected.size());
        assertThat(result, is(expected));
        assertThat(result, IsIterableContainingInOrder.contains(expected.toArray()));

    }

    private List<No> ofListM1() {
        return Arrays.asList(
                No.of("java.util.List", "add[ifpb.gpes.jdt.samples.A]", "boolean", "ifpb.gpes.jdt.samples.D", "m2[]", null),
                No.of("ifpb.gpes.jdt.samples.A", "getElements[]", "java.util.List<ifpb.gpes.jdt.samples.A>", "ifpb.gpes.jdt.samples.D", "m2[]", "add[ifpb.gpes.jdt.samples.A]"),
                No.of("java.util.List", "remove[java.lang.Object]", "boolean", "ifpb.gpes.jdt.samples.D", "m3[]", null),
                No.of("ifpb.gpes.jdt.samples.A", "getElements[]", "java.util.List<ifpb.gpes.jdt.samples.A>", "ifpb.gpes.jdt.samples.D", "m3[]", "remove[java.lang.Object]"),
                No.of("java.util.List", "isEmpty[]", "boolean", "ifpb.gpes.jdt.samples.D", "m3[]", null),
                No.of("ifpb.gpes.jdt.samples.A", "getElements[]", "java.util.List<ifpb.gpes.jdt.samples.A>", "ifpb.gpes.jdt.samples.D", "m3[]", "isEmpty[]"),
                No.of("java.util.List", "iterator[]", "java.util.Iterator<ifpb.gpes.jdt.samples.A>", "ifpb.gpes.jdt.samples.D", "m2[]", null),
                No.of("ifpb.gpes.jdt.samples.A", "getElements[]", "java.util.List<ifpb.gpes.jdt.samples.A>", "ifpb.gpes.jdt.samples.D", "m2[]", "iterator[]"),
                No.of("java.util.List", "listIterator[]", "java.util.ListIterator<ifpb.gpes.jdt.samples.A>", "ifpb.gpes.jdt.samples.D", "m2[]", null),
                No.of("ifpb.gpes.jdt.samples.A", "getElements[]", "java.util.List<ifpb.gpes.jdt.samples.A>", "ifpb.gpes.jdt.samples.D", "m2[]", "listIterator[]"),
                No.of("java.util.List", "iterator[]", "java.util.Iterator<ifpb.gpes.jdt.samples.A>", "ifpb.gpes.jdt.samples.I", "semRetorno", null),
                No.of("ifpb.gpes.jdt.samples.A", "getElements[]", "java.util.List<ifpb.gpes.jdt.samples.A>", "ifpb.gpes.jdt.samples.I", "semRetorno", "iterator[]"),
                No.of("java.util.List", "hashCode[]", "int", "ifpb.gpes.jdt.samples.ClasseAnonima", "m1[]", null),
                No.of("ifpb.gpes.jdt.samples.A", "getElements[]", "java.util.List<ifpb.gpes.jdt.samples.A>", "ifpb.gpes.jdt.samples.ClasseAnonima", "m1[]", "hashCode[]"));

    }

    //TODO: Refatorar para a classe de negocio
    // talvez criar uma classe que encapsule esse processamento
    private List<No> ofAnonymousClass() {
//        SmartAllVisitor visitor = new SmartAllVisitor();
//        String path = "/Users/job/Documents/dev/gpes/parse-review/parse-jdt/src/test/java/ifpb/gpes/jdt/samples/ClasseAnonima.java";
//        String[] sources = {"/Users/job/Documents/dev/gpes/parse-review/parse-jdt/src/test/java/"};
//        SmartFile smart = SmartFile.from(Paths.get(path));
//        SmartASTParser parser = SmartASTParser.from(sources);
//
//        Stream<Path> files = smart.extension(".java");
//
//        files.forEach(p -> {
//            parser.updateUnitName(p);
//            parser.acceptVisitor(visitor);
//        });
//        return visitor.methodsCall();
        Project project = Project
                .root("/Users/job/Documents/dev/gpes/parse-review/parse-jdt/")
                .path("src/test/java/ifpb/gpes/jdt/samples/ClasseAnonima.java") // root
                .sources("src/test/java/") // root - não obrigatorio
                .filter(".java");

        return  Parse.with(Parse.ParseStrategies.JDT).from(project);
 
    }

}
