package ifpb.gpes.jdt;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;

/**
 *
 * @author Juan
 */
public class MyAnotherVisitor extends ASTVisitor{
    @Override
    public boolean visit(MethodInvocation mi){
        IMethodBinding imb = mi.resolveMethodBinding();
        System.out.println("who invoked " + mi.getExpression());
        if(imb != null)
            infos(imb);
        else
            System.out.println(mi.getName() + " - erro");
        
        return super.visit(mi);
    }
    
    private void infos(IMethodBinding imb){
        System.out.println("name " + imb.getName());
        System.out.println("return type " + imb.getReturnType().getQualifiedName());
        System.out.println("\n");
    }
}
