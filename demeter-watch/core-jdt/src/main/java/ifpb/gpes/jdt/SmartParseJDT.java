package ifpb.gpes.jdt;

import ifpb.gpes.Call;
import ifpb.gpes.Project;
import org.eclipse.jdt.core.dom.ASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * A concrete implementation of the {@link ParseJDT} interface for parsing Java projects using JDT (Java Development Tools).
 * <p>
 * This class utilizes an {@link ASTVisitor} to traverse the abstract syntax tree (AST) of Java source files and collect
 * information about method calls within the project. It aggregates this information into a list of {@link Call} objects.
 * </p>
 *
 * @see ParseJDT
 * @see DefaultVisitor
 * @see ASTVisitor
 */
public class SmartParseJDT implements ParseJDT {

    private List<Call> elements = new ArrayList<>();
    private ASTVisitor visitor;

    /**
     * Creates an instance of {@code SmartParseJDT} with a default {@link DefaultVisitor}.
     */
    public SmartParseJDT() {
        this.visitor = new DefaultVisitor(elements);
    }

    /**
     * Creates an instance of {@code SmartParseJDT} with the specified {@link ASTVisitor}.
     *
     * @param visitor The {@link ASTVisitor} to be used for traversing the AST.
     */
    private SmartParseJDT(ASTVisitor visitor) {
        this.visitor = visitor;
    }

    /**
     * Creates a new {@code SmartParseJDT} instance with the specified {@link ASTVisitor}.
     *
     * @param visitor The {@link ASTVisitor} to be used for traversing the AST.
     * @return A new {@code SmartParseJDT} instance.
     */
    public static ParseJDT of(ASTVisitor visitor) {
        return new SmartParseJDT(visitor);
    }

    /**
     * Parses the provided {@link Project} and collects method call information.
     *
     * @param project The {@link Project} to be parsed.
     * @return A {@link List} of {@link Call} objects representing the method calls found in the project.
     */
    @Override
    public List<Call> from(Project project) {
        DefaultASTParser parser = DefaultASTParser.from(new String[]{project.sources()}, project.classpath());
        project.files().forEach(p -> {
            parser.updateUnitName(p);
            parser.acceptVisitor(visitor);
        });
        return elements;
    }

    /**
     * Sets the {@link ASTVisitor} to be used for traversing the AST.
     *
     * @param visitor The {@link ASTVisitor} to be used.
     * @return This {@code SmartParseJDT} instance for method chaining.
     */
    @Override
    public ParseJDT visitor(ASTVisitor visitor) {
        this.visitor = visitor;
        return this;
    }
}
