package ifpb.gpes.jdt;

import ifpb.gpes.No;
import ifpb.gpes.Parse;
import ifpb.gpes.Project;
import ifpb.gpes.SingletonPath;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ricardo Job
 */
public class PrintASTVisitorTest {

    private final PrintASTVisitor visitor = new PrintASTVisitor();
    private static final Logger logger = Logger.getLogger(PrintASTVisitorTest.class.getName());

    @Test
    public void testPrintVisitor() {
        Project project = Project
                .root(SingletonPath.ROOT)
                .path("src/test/java/ifpb/gpes/jdt/samples/ClasseAnonima.java") // root
                .sources("src/test/java/") // root - não obrigatorio
                .filter(".java");

        Parse.ParseStrategy strategy = Parse.SmartParseJDT.of(visitor);
        List<No> returnList = Parse.with(strategy).from(project);

        assertNotNull(returnList);
        assertTrue(returnList.isEmpty());
        assertFalse("".equals(visitor.visitToString().trim()));
        logger.log(Level.INFO, "---Visitor begin---");
        logger.log(Level.INFO, visitor.visitToString());
        logger.log(Level.INFO, "---Visitor end  ---");

    }

}
