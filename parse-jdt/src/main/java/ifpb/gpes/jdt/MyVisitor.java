package ifpb.gpes.jdt;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;

/**
 *
 * @author Juan
 */
public class MyVisitor extends ASTVisitor {

    @Override
    public boolean visit(MethodDeclaration md) {       
        infos(md);
        return super.visit(md);
    }

    private void infos(MethodDeclaration node) {
        IMethodBinding resolveMethodBinding = node.resolveBinding();
        System.out.println(resolveMethodBinding);
        System.out.println("name " + resolveMethodBinding.getName());
    
        ITypeBinding itb = resolveMethodBinding.getReturnType();
        
        System.out.println("returnType " + itb.getQualifiedName());
        
        System.out.println("\n");

    }

}
