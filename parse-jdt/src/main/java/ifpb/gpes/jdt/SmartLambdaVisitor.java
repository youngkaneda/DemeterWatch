package ifpb.gpes.jdt;

import ifpb.gpes.No;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class SmartLambdaVisitor extends ASTVisitor {

    private List<No> ns = new ArrayList<>();
    private Expression ex;

    SmartLambdaVisitor(List<No> ns, Expression ex) {
        this.ex = ex;
        this.ns = ns;
    }

    @Override
    public boolean visit(ExpressionStatement state) {

        state.accept(new SmartLambdaMIVisitor(ns, ex));

        return super.visit(state);
    }

}
