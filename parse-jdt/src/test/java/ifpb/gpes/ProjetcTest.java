package ifpb.gpes;

import ifpb.gpes.Parse.ParseStrategies;
import ifpb.gpes.Project.ProjectTypes;
import ifpb.gpes.jdt.PrintASTVisitor;
import ifpb.gpes.jdt.SmartASTParser;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ricardo Job
 */
public class ProjetcTest {

    @Test
    public void testProject() {
        Project project = Project
                .root("/Users/job/Documents/dev/gpes/parse-review/parse-jdt/")
//                .type(ProjectTypes.MAVEN)
                .path("src/test/java/ifpb/gpes/jdt/samples/ClasseAnonima.java") // root
                .sources("src/test/java/") // root - não obrigatorio
                .filter(".java");

        Parse parse = Parse
                .with(ParseStrategies.JDT);

        List<No> result = parse.from(project);

        assertNotNull(project);
        assertNotNull(parse);
        assertNotNull(result);

//        result.forEach(no -> System.out.println(no.callGraph()));

        PrintASTVisitor print = new PrintASTVisitor();
        Parse.ParseStrategy visitor = new Parse.SmartParseJDT().visitor(print);
        Parse.with(visitor).from(project).forEach(no -> System.out.println(no.callGraph()));
        System.out.println(print.visitToString());
        
//SmartAllVisitor visitor = new SmartAllVisitor();
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
    }

}
