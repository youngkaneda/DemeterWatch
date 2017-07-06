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

    public static interface ParseStrategy {

        public List<No> from(Project project);
    }

    public static interface ParseJDT extends ParseStrategy {

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

    public static class SmartParseJDT implements ParseJDT {

        private List<No> elements = new ArrayList<>();
        private ASTVisitor visitor;

        //TODO: isso não está bom. Just do It!
        protected SmartParseJDT() {
            this.elements = new ArrayList<>();
            this.visitor = new SmartAllVisitor(elements);
        }

        private SmartParseJDT(ASTVisitor visitor) {
            this.visitor = visitor;
        }

        public static ParseJDT of(ASTVisitor visitor) {
            return new SmartParseJDT(visitor);
        }

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
