package ifpb.gpes.jdt;

import ifpb.gpes.No;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class SmartAllVisitor extends ASTVisitor {

    private List<No> ns = new ArrayList<>();

    private MethodDeclaration currentMethodDeclaration;
    private Expression currentExpression;
    //TODO: criar uma classe para fazer substituir
    private final Stack<MethodDeclaration> stackMethodDeclaration = new Stack<>();
    private final Stack<MethodInvocation> stackMethodInvocation = new Stack<>();

    @Override
    public boolean visit(MethodDeclaration md) {
        this.currentMethodDeclaration = md;
        this.currentExpression = null;
        return super.visit(md);
    }

    @Override
    public boolean visit(LambdaExpression node) {
        this.currentExpression = node;
        return super.visit(node);
    }

    @Override
    public void endVisit(LambdaExpression node) {
        this.currentExpression = null;
        super.endVisit(node);
    }

    @Override
    public boolean visit(AnonymousClassDeclaration node) {
        this.stackMethodDeclaration.push(currentMethodDeclaration);
        return super.visit(node);
    }

    @Override
    public void endVisit(AnonymousClassDeclaration node) {
        this.currentMethodDeclaration = stackMethodDeclaration.pop();
        super.endVisit(node);
    }

    @Override
    public boolean visit(MethodInvocation mi) {
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

        ITypeBinding callClass = currentMethodDeclaration.resolveBinding().getDeclaringClass();
        String c = callClassToString(callClass);

        no.setC(c);

        bindings = currentMethodDeclaration.resolveBinding().getParameterTypes();

        String m1 = fillMethodName(currentMethodDeclaration.getName().getIdentifier(), bindings);
        no.setM1(m1);

        if (currentExpression != null) { //lambda
            no.setC(currentExpression
                    .resolveTypeBinding()
                    .getQualifiedName());
            no.setM1(currentExpression
                    .resolveTypeBinding()
                    .getFunctionalInterfaceMethod()
                    .getName());
        }

        int count = ns.size();

                
        //TODO: podemos usar a ideia do Stack para analisar?
        no.setInv(updateInv(mi));

        String methodInvocation = getMethodInvocation(count, mi.getName().toString());

        no.setMi(methodInvocation);
        ns.add(no);
        
        //TODO: com o methodInvocation funcionou. 
        //As informações são iguais.
        // precisamos capturar os argumentos
        if(!stackMethodInvocation.isEmpty() && stackMethodInvocation.peek()!=null){
//            System.out.println(methodInvocation + " < - > "+stackMethodInvocation.peek().getName());
        }
        stackMethodInvocation.push(mi);
        return super.visit(mi);
    }

    @Override
    public void endVisit(MethodInvocation node) {
        stackMethodInvocation.pop();
        super.endVisit(node); 
    }

    
    @Override
    public void endVisit(MethodDeclaration node) {
        this.currentMethodDeclaration = node;
        this.currentExpression = null;
        super.visit(node);
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
        if (count < 1) {
            return null;
        }

        if (!ns.get(count - 1).getInv().contains(methodName)) {
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

    public List<No> methodsCall() {
        return Collections.unmodifiableList(ns);
    }

    public List<No> methodsCallFilter() {
        return Collections
                .unmodifiableList(ns.stream()
                        .filter(t -> t.getMi() != null || "null".equalsIgnoreCase(t.getMi()))
                        .collect(Collectors.toList()));
    }
}
