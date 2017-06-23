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
    public boolean visit(ExpressionStatement state){
        No no = new No();
        no.setC(ex.resolveTypeBinding().getQualifiedName());
        
        //já que interface funcional possui apenas um metodo - [0]
        no.setM1(ex.resolveTypeBinding().getDeclaredMethods()[0].getName());
        
        state.accept(new ASTVisitor(){
            @Override
            public boolean visit(MethodInvocation mi){
                
                no.setA(mi.resolveMethodBinding().getDeclaringClass().getQualifiedName());
                no.setM(mi.getName().getFullyQualifiedName());
                no.setRt(mi.resolveMethodBinding().getReturnType().getQualifiedName());
                
                return super.visit(mi);
            }
        });
        
        System.out.println(no);
        
        return super.visit(state);
    }

}
