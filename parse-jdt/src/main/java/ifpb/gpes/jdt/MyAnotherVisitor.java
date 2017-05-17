package ifpb.gpes.jdt;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

/**
 *
 * @author Juan
 */
public class MyAnotherVisitor extends ASTVisitor {

    private static List<No> metodos = new ArrayList<>();
    private static int count = 0;
    
    @Override
    public boolean visit(MethodInvocation mi) {
        IMethodBinding imb = mi.resolveMethodBinding();
        System.out.println("\twho invoked " + mi.getExpression() + ": " + mi.getName());
        if (imb != null) {
            infos(imb);
            resolveInvocation(mi);
        } else {
            System.out.println("\t" + mi.getName() + " - erro");
        }
        
        return super.visit(mi);
    }

    private void infos(IMethodBinding imb) {
        System.out.println("\tname " + imb.getName());
        System.out.println("\treturn type " + imb.getReturnType().getQualifiedName());
    }

    @Override
    public boolean visit(MethodDeclaration md) {
        System.out.println("md: " + md.getName());
        IMethodBinding resolveMethodBinding = md.resolveBinding();
        resolveDeclaration(md);
//        infos(resolveMethodBinding);
        return super.visit(md);
    }

    private static void resolveInvocation(MethodInvocation mi) {
        String nome = mi.getName().getFullyQualifiedName();
        String returnType = mi.resolveMethodBinding().getReturnType().getQualifiedName();
        
        No no = new No(nome, returnType);
        
        metodos.get(count-1).addMethod(new Method(nome, returnType));
    }
    
    private static void resolveDeclaration(MethodDeclaration md) {
        if(count != 0)
            count++;
        
        String nome = md.getName().getFullyQualifiedName();
        String returnType = md.resolveBinding().getReturnType().getQualifiedName();
        
        No no = new No(nome, returnType);
        
        metodos.add(no);
        
        if(count == 0)
            count++;
    }

    public void showLista(){
        System.out.println(metodos.toString());
    }
}
