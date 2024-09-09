package ifpb.gpes.jdt;


import ifpb.gpes.ParseStrategy;
import org.eclipse.jdt.core.dom.ASTVisitor;

/**
 * An interface representing a strategy for parsing Java code using Eclipse JDT.
 * <p>
 * This interface extends {@link ParseStrategy} and provides additional functionality specific to JDT-based parsing.
 * It allows the configuration of a visitor that can be used to traverse and analyze the abstract syntax tree (AST) produced
 * by the JDT parser.
 * </p>
 *
 * @see ParseStrategy
 * @see ASTVisitor
 */
public interface ParseJDT extends ParseStrategy {

    /**
     * Configures the visitor to be used during the parsing process.
     *
     * @param visitor The {@link ASTVisitor} to be used for traversing and analyzing the AST.
     * @return The current instance of {@code ParseJDT} for method chaining.
     */
    public ParseJDT visitor(ASTVisitor visitor);
}