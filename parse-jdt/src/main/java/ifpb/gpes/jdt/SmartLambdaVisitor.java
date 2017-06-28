package ifpb.gpes.jdt;

import ifpb.gpes.No;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class SmartLambdaVisitor extends ASTVisitor {

    private List<No> ns = new ArrayList<>();
    private Expression ex;

    SmartLambdaVisitor(List<No> ns, Expression ex) {
        this.ex = ex;
        this.ns = ns;
    }

    @Override
    public boolean visit(VariableDeclarationStatement vds) {

        vds.fragments().forEach((frag) -> {
            if (frag instanceof VariableDeclarationFragment) {
                VariableDeclarationFragment vdf = (VariableDeclarationFragment) frag;
                if (vdf.getInitializer() instanceof ClassInstanceCreation) {
                    ClassInstanceCreation cic = (ClassInstanceCreation) vdf.getInitializer();
                    if (cic.getAnonymousClassDeclaration() != null) {
                        cic.getAnonymousClassDeclaration().accept(new SmartRecursiveVisitor());
                    }
                }
                if (vdf.getInitializer() instanceof LambdaExpression) {
                    LambdaExpression le = (LambdaExpression) vdf.getInitializer();
                    int nodeType = le.getBody().getNodeType();
                    if (nodeType == ASTNode.METHOD_INVOCATION) {
                        le.accept(new SmartLambdaMIVisitor(ns, le));
                    } else {
                        le.accept(new SmartLambdaVisitor(ns, le));
                    }
                }
                if (vdf.getInitializer() instanceof MethodInvocation) {
                    MethodInvocation mi = (MethodInvocation) vdf.getInitializer(); 
                    mi.accept(new SmartLambdaMIVisitor(ns, ex));
                }
            }
        });

        return super.visit(vds);
    }

    @Override

    public boolean visit(ExpressionStatement state) {

        state.accept(new SmartLambdaMIVisitor(ns, ex));

        return super.visit(state);
    }

}
