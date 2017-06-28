package ifpb.gpes.jdt;

import ifpb.gpes.No;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 02/06/2017, 11:20:18
 */
public class SmartBlockVisitor extends ASTVisitor {

    private final List<No> ns;
    private final MethodDeclaration methodDeclaration;

    protected SmartBlockVisitor(MethodDeclaration methodDeclarion, List<No> no) {
        this.methodDeclaration = methodDeclarion;
        this.ns = no;
    }

    @Override
    public boolean visit(MethodInvocation mi) {
        parseArguments(mi.arguments());
        ITypeBinding callClass = methodDeclaration.resolveBinding().getDeclaringClass();

        No no = new No();
        String a = "SAD";
        String returnType = "SADNESS";
        IMethodBinding imb = mi.resolveMethodBinding();
        ITypeBinding[] bindings = {};
        if (imb != null) {
            bindings = imb.getParameterTypes();
            a = imb.getDeclaringClass().getBinaryName();
            returnType = imb.getReturnType().getQualifiedName();
        }

        no.setA(a);
        no.setRt(returnType);

        String m = fillMethodName(mi.getName().toString(), bindings);
        no.setM(m);

        String c = callClassToString(callClass);

        no.setC(c);

        bindings = methodDeclaration.resolveBinding().getParameterTypes();

        String m1 = fillMethodName(methodDeclaration.getName().getIdentifier(), bindings);
        no.setM1(m1);

        int count = ns.size();

        no.setInv(updateInv(mi));

        String methodInvocation = getMethodInvocation(count, mi.getName().toString());

        no.setMi(methodInvocation);

        ns.add(no);

        return super.visit(mi);
    }

    public String callClassToString(ITypeBinding callClass) {
        if (callClass.isAnonymous()) {
            return callClass.getSuperclass().getQualifiedName();
        }
        return callClass.getQualifiedName();
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

    private String fillMethodName(String methodName, ITypeBinding[] bindings) {
        String prefix = methodName + "[";
        String sufix = "]";
        return Arrays
                .asList(bindings)
                .stream()
                .map(b -> b.getQualifiedName())
                .collect(Collectors.joining(", ", prefix, sufix));
    }

    private void parseArguments(List<Expression> arguments) {
        for (Expression argument : arguments) {
            if (argument.getNodeType() == ASTNode.METHOD_INVOCATION) {
                argument.accept(new SmartBlockVisitor(methodDeclaration, ns));
            } else if(argument.getNodeType() == ASTNode.LAMBDA_EXPRESSION){
                Expression le = (LambdaExpression) argument;
                //NAO TA FUNCNIONANDO NENHUMA DROGA DE RESOLVE BINDING, PQ EU N SEI!!!!!!
                //NAO SEI PQ A ARVORE MOSTRA OS BINDING MAS QUANDO TENTO AQUI N FUNCIONA!!!INFERNO
                //le.accept(new SmartLambdaMIVisitor(ns, argument));
            }
        }
    }
}
