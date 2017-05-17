package ifpb.gpes.jdt;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

/**
 *
 * @author Juan
 */
public class MyAnotherVisitor extends ASTVisitor {

    @Override
    public boolean visit(MethodInvocation mi) {
        IMethodBinding imb = mi.resolveMethodBinding();

        System.out.println("\twho invoked " + mi.getExpression() + ": " + mi.getName());
        if (imb != null) {
            infos(imb);
        } else {
            System.out.println("\t" + mi.getName() + " - erro");
        }

        return super.visit(mi);
    }

    private void infos(IMethodBinding imb) {
        System.out.println("\tname " + imb.getName());
        System.out.println("\treturn type " + imb.getReturnType().getQualifiedName());
//        System.out.println("\n");
    }

    @Override
    public boolean visit(MethodDeclaration md) {
        System.out.println("md: " + md.getName());
//        IMethodBinding resolveMethodBinding = md.resolveBinding();
//        infos(resolveMethodBinding);
        return super.visit(md);
    }

//    private void infos(MethodDeclaration node) {
//        
//        System.out.println(resolveMethodBinding);
//        System.out.println("name " + resolveMethodBinding.getName());
//    
//        ITypeBinding itb = resolveMethodBinding.getReturnType();
//        
//        System.out.println("returnType " + itb.getQualifiedName());
//        
//        System.out.println("\n");
//
//    }
}
