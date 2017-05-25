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
    private StringBuilder builder;

    public MyAnotherVisitor() {
        this("Not Class");
    }

    public MyAnotherVisitor(String fileName) {
        this.builder = new StringBuilder("class:");
        this.builder.append(fileName).append("\n");
    }

    @Override
    public boolean visit(MethodInvocation mi) {
        IMethodBinding imb = mi.resolveMethodBinding();
//        System.out.println("\twho invoked "  + mi.getName() + "? " + mi.getExpression());
        this.builder
                .append("\twho invoked ")
                .append(mi.getName())
                .append("? ")
                .append(mi.getExpression())
                .append("\n");

        if (imb != null) {
            infos(imb);
            resolveInvocation(mi);
        } else {
            this.builder
                    .append("\t")
                    .append(mi.getName())
                    .append(" - erro")
                    .append("\n");
        }

        return super.visit(mi);
    }

    private void infos(IMethodBinding imb) {
        this.builder
                .append("\twho invoked type ")
                .append(imb.getDeclaringClass().getQualifiedName())
                .append("\tname ")
                .append(imb.getName())
                .append("\treturn type ")
                .append(imb.getReturnType().getQualifiedName())
                .append("\n");
    }

    @Override
    public boolean visit(MethodDeclaration md) {
        this.builder
                .append("md: ")
                .append(md.getName())
                .append("\n");
        IMethodBinding resolveMethodBinding = md.resolveBinding();
        resolveDeclaration(md);
//        infos(resolveMethodBinding);
        return super.visit(md);
    }

    private static void resolveInvocation(MethodInvocation mi) {
        String nome = mi.getName().getFullyQualifiedName();
        String returnType = mi.resolveMethodBinding().getReturnType().getQualifiedName();

        No no = new No(nome, returnType);

        metodos.get(count - 1).addMethod(new Method(nome, returnType));
    }

    private static void resolveDeclaration(MethodDeclaration md) {
        if (count != 0) {
            count++;
        }

        String nome = md.getName().getFullyQualifiedName();
        String returnType = md.resolveBinding().getReturnType().getQualifiedName();

        No no = new No(nome, returnType);

        metodos.add(no);

        if (count == 0) {
            count++;
        }
    }

    public void showLista() {
        this.builder
                .append(metodos.toString())
                .append("\n");
        System.out.println(this.builder.toString());
    }
}
