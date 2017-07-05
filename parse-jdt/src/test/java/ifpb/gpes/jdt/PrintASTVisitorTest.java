/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.gpes.jdt;

import ifpb.gpes.io.SmartFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ricardo Job
 */
public class PrintASTVisitorTest {

    private PrintASTVisitor visitor = new PrintASTVisitor();
//    private SmartMethodVisitor visitor = new SmartMethodVisitor();
//    private SmartAllVisitor visitor = new SmartAllVisitor();

    @Test
    public void testPrintVisitor() {
//        String path = "/Users/job/Documents/dev/gpes/parse-review/parse-jdt/src/main/java/ifpb/gpes/jdt/samples/ClasseAnonima.java";
//        String sources = "/Users/job/Documents/dev/gpes/parse-review/parse-jdt/src/main/java/";
        String path = "/Users/job/Documents/dev/gpes/parse-review/parse-jdt/src/test/java/ifpb/gpes/jdt/samples/ClasseAnonima.java";
        String[] sources = {"/Users/job/Documents/dev/gpes/parse-review/parse-jdt/src/test/java/"};
        SmartFile smart = SmartFile.from(Paths.get(path));
        SmartASTParser parser = SmartASTParser.from(sources);

        Stream<Path> files = smart.extension(".java");

        files.forEach(p -> {
            parser.updateUnitName(p);
            parser.acceptVisitor(visitor);
        });

        System.out.println(visitor.visitToString());
//        visitor.methodsCall().forEach(a -> System.out.println(a.noOf()));

    }

}
