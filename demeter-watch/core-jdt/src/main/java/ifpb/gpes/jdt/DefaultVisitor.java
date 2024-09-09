package ifpb.gpes.jdt;

import ifpb.gpes.Call;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.*;

/**
 * An AST visitor for analyzing Java source code. Collects method calls and lambda expressions,
 * and tracks the context of method invocations, including their origin and the methods they call.
 */
public class DefaultVisitor extends ASTVisitor {

    private List<Call> calls;

    private MethodDeclaration currentMethodDeclaration;
    private Expression currentExpression;
    private final Stack<MethodDeclaration> stackMethodDeclaration = new Stack<>();

    /**
     * Constructs a {@code DefaultVisitor} with an empty list of calls.
     */
    public DefaultVisitor() {
        this(new ArrayList<>());
    }

    /**
     * Constructs a {@code DefaultVisitor} with the specified list of calls.
     *
     * @param elements The list of {@code Call} objects to populate.
     */
    public DefaultVisitor(List<Call> elements) {
        this.calls = elements;
    }

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
    public boolean visit(ExpressionMethodReference node) {
        Expression ex = node.getExpression();
        Expression castedEx = (Expression) node;
        String methodName = fillMethodName(
            node.getName().getFullyQualifiedName(),
            node.resolveTypeBinding().getTypeArguments()
        );
        IMethodBinding imb = castedEx.resolveTypeBinding().getFunctionalInterfaceMethod();
        String calledInMethod = fillMethodName(imb.getName(), imb.getParameterTypes());

        Call no = new Call();

        no.setClassType(ex.resolveTypeBinding().getQualifiedName());
        no.setMethodName(methodName);
        no.setReturnType(node.getName().resolveTypeBinding().toString());
        no.setCalledInClass(castedEx.resolveTypeBinding().getQualifiedName());
        no.setCalledInMethod(calledInMethod);
        no.setCalledInMethodReturnType(imb.getReturnType().getQualifiedName());
        no.setCallMethod(null);

        no.setInvokedBy(ex.toString());
        calls.add(no);

        return super.visit(node);
    }

    @Override
    public void endVisit(ExpressionMethodReference node) {
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
        Call no = new Call();
        String classType = "not found";
        String returnType = "not found";
        IMethodBinding imb = mi.resolveMethodBinding();
        ITypeBinding[] bindings = {};
        if (imb != null) {
            bindings = imb.getParameterTypes();
            classType = imb.getDeclaringClass().getBinaryName();
            returnType = imb.getReturnType().getQualifiedName();
        }

        no.setClassType(classType);
        no.setReturnType(returnType);

        String methodName = fillMethodName(mi.getName().toString(), bindings);
        no.setMethodName(methodName);

        ITypeBinding callClass = inferCalledInClass(mi, currentMethodDeclaration);

        String calledInClass;

        if (callClass != null) {
            calledInClass = callClassToString(callClass);
        } else {
            TypeDeclaration parent = (TypeDeclaration) currentMethodDeclaration.getParent();
            calledInClass = parent.getName().getFullyQualifiedName();
        }

        no.setCalledInClass(calledInClass);

        bindings = inferMethodDeclarationParameters(currentMethodDeclaration);

        if (currentMethodDeclaration != null) {
            String calledInMethod = fillMethodName(currentMethodDeclaration.getName().getIdentifier(), bindings);
            no.setCalledInMethod(calledInMethod);

            if (currentMethodDeclaration.resolveBinding() != null) {
                no.setCalledInMethodReturnType(currentMethodDeclaration.resolveBinding().getReturnType().getQualifiedName());
            } else {
                Type returnType2 = currentMethodDeclaration.getReturnType2();
                no.setCalledInMethodReturnType(inferCalledInMethodReturnTypeValue(returnType2, currentMethodDeclaration).getQualifiedName());
            }
        }

        if (currentExpression != null) {
            no.setCalledInClass(currentExpression
                .resolveTypeBinding()
                .getQualifiedName());
            ITypeBinding[] bindingsLambda = currentExpression.resolveTypeBinding().
                getTypeArguments();
            String calledInMethodLambda = fillMethodName(currentExpression
                .resolveTypeBinding()
                .getFunctionalInterfaceMethod()
                .getName(), bindingsLambda);
            no.setCalledInMethod(calledInMethodLambda);
            String calledInMethodLambdaReturnType = currentExpression
                .resolveTypeBinding()
                .getFunctionalInterfaceMethod()
                .getReturnType().getQualifiedName();
            no.setCalledInMethodReturnType(calledInMethodLambdaReturnType);
        }

        int count = calls.size();

        no.setInvokedBy(updateInv(mi));

        String methodInvocation = getMethodInvocation(count, mi.getName().toString());

        no.setCallMethod(methodInvocation);
        calls.add(no);
        return super.visit(mi);
    }

    @Override
    public void endVisit(MethodDeclaration node) {
        this.currentMethodDeclaration = node;
        this.currentExpression = null;
        super.visit(node);
    }

    /**
     * Converts the given class type binding to a string representation.
     *
     * @param callClass The class type binding.
     * @return The string representation of the class type.
     */
    public String callClassToString(ITypeBinding callClass) {
        if (callClass.isAnonymous()) {
            return callClass.getSuperclass().getQualifiedName();
        }
        return callClass.getQualifiedName();
    }

    /**
     * Updates the invocation string for the method invocation.
     *
     * @param mi The method invocation node.
     * @return The invocation string.
     */
    private String updateInv(MethodInvocation mi) {
        Expression inv = mi.getExpression();
        if (inv == null) {
            return "this";
        }
        return inv.toString();
    }

    /**
     * Retrieves the method invocation based on the current count and method name.
     *
     * @param count The current count of method calls.
     * @param methodName The method name to check.
     * @return The method invocation string or {@code null} if not found.
     */
    private String getMethodInvocation(int count, String methodName) {
        if (count < 1) {
            return null;
        }

        if (!calls.get(count - 1).getInvokedBy().contains(methodName)) {
            return null;
        }

        return calls.get(count - 1).getMethodName();
    }

    /**
     * Formats the method name with its parameter types.
     *
     * @param methodName The method name.
     * @param bindings The method parameter bindings.
     * @return The formatted method name.
     */
    private String fillMethodName(String methodName, ITypeBinding[] bindings) {
        String prefix = methodName + "[";
        String sufix = "]";
        return Arrays.stream(bindings)
            .map(ITypeBinding::getQualifiedName)
            .collect(Collectors.joining(", ", prefix, sufix));
    }

    /**
     * Infers the class in which the method is called.
     *
     * @param mi The method invocation node.
     * @param md The method declaration node.
     * @return The class type binding in which the method is called.
     */
    private ITypeBinding inferCalledInClass(MethodInvocation mi, MethodDeclaration md) {
        if (md == null) {
            ASTNode parent = mi.getParent();
            while (!(parent instanceof TypeDeclaration)) {
                parent = parent.getParent();
            }
            return ((TypeDeclaration) parent).resolveBinding();
        }
        IMethodBinding binding = md.resolveBinding();
        return binding != null ? binding.getDeclaringClass() : ((TypeDeclaration) md.getParent()).resolveBinding();
    }

    /**
     * Infers the parameter types of the method declaration.
     *
     * @param md The method declaration node.
     * @return The array of parameter type bindings.
     */
    private ITypeBinding[] inferMethodDeclarationParameters(MethodDeclaration md) {
        if (md == null) {
            return new ITypeBinding[0];
        }
        IMethodBinding binding = md.resolveBinding();
        if (binding != null) {
            return md.resolveBinding().getParameterTypes();
        }
        return (ITypeBinding[]) md.parameters()
            .stream()
            .map(p -> {
                IVariableBinding bnd = ((SingleVariableDeclaration) p).resolveBinding();
                return bnd != null ? bnd.getType() : new DummyTypeBinding(((SingleVariableDeclaration) p).getType());
            })
            .toArray(ITypeBinding[]::new);
    }

    /**
     * Infers the return type value of the method declaration.
     *
     * @param type The return type.
     * @param md The method declaration node.
     * @return The inferred return type binding.
     */
    private ITypeBinding inferCalledInMethodReturnTypeValue(Type type, MethodDeclaration md) {
        if (!md.isConstructor()) {
            return type.resolveBinding() == null ? new DummyTypeBinding(type) : type.resolveBinding();
        } else {
            return new DummyTypeBinding() {
                @Override
                public String getQualifiedName() {
                    return PrimitiveType.VOID.toString();
                }
            };
        }
    }

    /**
     * Returns an unmodifiable list of method calls collected by this visitor.
     *
     * @return An unmodifiable list of {@code Call} objects.
     */
    public List<Call> methodsCall() {
        return Collections.unmodifiableList(calls);
    }
}
