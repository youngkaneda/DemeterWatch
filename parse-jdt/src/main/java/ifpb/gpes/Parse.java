package ifpb.gpes;

import ifpb.gpes.jdt.SmartASTParser;
import ifpb.gpes.jdt.SmartAllVisitor;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTVisitor;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 05/07/2017, 15:28:08
 */
public class Parse {

    private final ParseStrategy type;

    private Parse(ParseStrategy type) {
        this.type = type;
    }

    public static Parse with(ParseStrategy type) {
        return new Parse(type);
    }

    public List<No> from(Project project) {
        return type.from(project);
    }

    static interface ParseStrategy {

        public List<No> from(Project project);
    }

    static interface ParseJDT extends ParseStrategy {

        public ParseJDT visitor(ASTVisitor visitor);
    }

    public enum ParseStrategies implements ParseStrategy {
        JDT {
            @Override
            public List<No> from(Project project) {
                return new SmartParseJDT().from(project);
            }

        }
    }

    static class SmartParseJDT implements ParseJDT {

        private final List<No> elements = new ArrayList<>();
        //TODO: isso não está bom. Just do It!
        private ASTVisitor visitor = new SmartAllVisitor(elements);

        @Override
        public List<No> from(Project project) {
            SmartASTParser parser = SmartASTParser.from(project.sources());

            project.files().forEach(p -> {
                parser.updateUnitName(p);
                parser.acceptVisitor(visitor);
            });
            return elements;
        }

        @Override
        public ParseJDT visitor(ASTVisitor visitor) {
            this.visitor = visitor;
            return this;
        }
    }

}
