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
    Expression ex;

    SmartLambdaVisitor(List<No> ns, Expression ex) {
        this.ex = ex;
        this.ns = ns;
    }

    @Override
    public boolean visit(ExpressionStatement state) {

        state.accept(new ASTVisitor() {
            @Override
            public boolean visit(MethodInvocation mi) {
                No no = new No();

                no.setC(ex.resolveTypeBinding().getQualifiedName());
                no.setM1(ex.resolveTypeBinding().getFunctionalInterfaceMethod().getName());
                no.setA(mi.resolveMethodBinding().getDeclaringClass().getQualifiedName());
                no.setM(mi.getName().getFullyQualifiedName());
                no.setRt(mi.resolveMethodBinding().getReturnType().getQualifiedName());

                no.setInv(updateInv(mi));
                no.setMi(getMethodInvocation(ns.size(), mi.getName().toString()));
                
                int count = ns.size();

                ns.add(no);

                return super.visit(mi);
            }
        });

        return super.visit(state);
    }

    private String updateInv(MethodInvocation mi) {
        Expression inv = mi.getExpression();
        if (inv == null) {
            return "nothing here";
        }

        String[] ms = inv.toString().split("\\.");
        int size = ms.length;

        return ms[size - 1];
    }

    private String getMethodInvocation(int count, String methodName) {
        if (count <= 1) {
//            return "null";
            return null;
        }
        
        if (!ns.get(count - 1).getInv().contains(methodName)) {
//            return "null";
            return null;
        }

        return ns.get(count - 1).getM();
    }

}
