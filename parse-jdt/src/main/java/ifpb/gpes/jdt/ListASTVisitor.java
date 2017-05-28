package ifpb.gpes.jdt;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 19/08/2016, 17:42:34
 */
public class ListASTVisitor extends ASTVisitor {

    @Override
    public boolean visit(FieldAccess node) {
        System.out.println("field: " + node.getName() + " ex: " + node.getExpression());
        return super.visit(node); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean visit(MethodInvocation node) {
        Expression expression = node.getExpression();
        ITypeBinding resolveTypeBinding = expression.resolveTypeBinding();
        if (isList(resolveTypeBinding)) {
            System.out.println("method " + node.getName());
            System.out.println("\texpression " + expression);
//            infoType(resolveTypeBinding, node);
        }

        System.out.println(
                String.format("type:%s; \tcall:%s; \texpression:%s;",
                        resolveTypeBinding.getBinaryName(),
                        node.getName(),
                        expression
                ));
        return super.visit(node);
    }

    private void infoMethod(MethodInvocation node) {
        IMethodBinding resolveMethodBinding = node.resolveMethodBinding();
        System.out.println("resolveMethodBinding " + resolveMethodBinding);
        System.out.println("resolveMethodBinding " + resolveMethodBinding.getName());
        System.out.println(resolveMethodBinding.getDeclaringClass());
        System.out.println("resolveMethodBinding " + resolveMethodBinding.getDeclaredReceiverType());
        System.out.println(resolveMethodBinding.getReturnType());
        System.out.println("\n");

    }

    private void infoType(ITypeBinding resolveTypeBinding, MethodInvocation node) {
        System.out.println("getBinaryName " + resolveTypeBinding.getBinaryName());
        System.out.println("resolveTypeBinding " + resolveTypeBinding.getName());
        System.out.println("getQualifiedName " + resolveTypeBinding.getQualifiedName());
        System.out.println("resolveMethodBinding " + node.resolveMethodBinding());
        IType boundType = (IType) resolveTypeBinding.getJavaElement();
        System.out.println("getJavaElement " + boundType);
        System.out.println("resolveTypeBinding().getDeclaringClass() " + node.getExpression().resolveTypeBinding().getComponentType());
        System.out.println("typeArguments " + node.typeArguments());
    }

    private static boolean isList(ITypeBinding resolveTypeBinding) {

        return resolveTypeBinding.getBinaryName().equals("java.util.List");
    }

    private void infoParent(MethodInvocation node) {
        switch (node.getParent().getNodeType()) {
            case ASTNode.METHOD_INVOCATION:
                MethodInvocation mi = (MethodInvocation) node.getParent();
                System.out.println("\tmi.get: " + mi.getName().getIdentifier());
                break;
            case ASTNode.FIELD_DECLARATION:
                FieldDeclaration field = (FieldDeclaration) node.getParent();
                System.out.println("\tfield.fragments().get: " + field.fragments().get(0));
                break;
            case ASTNode.METHOD_DECLARATION:
                MethodDeclaration mds = (MethodDeclaration) node.getParent();
                IMethod iMethodA = getIMethod(mds);
                System.out.println(
                        "\tMD: " + node.resolveTypeBinding().getQualifiedName()
                        + " " + mds.getName().getIdentifier()
                        + " " + iMethodA);
                break;
            case ASTNode.TYPE_DECLARATION:
                System.out.println(
                        "\tTD: " + node.resolveTypeBinding().getQualifiedName());
                break;
            case ASTNode.VARIABLE_DECLARATION_STATEMENT: {
                VariableDeclarationStatement st = (VariableDeclarationStatement) node
                        .getParent();
                VariableDeclarationFragment vdf = ((VariableDeclarationFragment) st
                        .fragments().get(0));
                ASTNode relevantParent = node.getRoot();
                if (relevantParent.getNodeType() == ASTNode.METHOD_DECLARATION) {
                    MethodDeclaration md = (MethodDeclaration) relevantParent;

                    System.out.println(
                            "\tVMD: " + node.resolveTypeBinding().getQualifiedName()
                            + "" + md.getName()
                                    .getIdentifier()
                            + "" + vdf.getName()
                                    .getIdentifier());

                }
                break;
            }
            case ASTNode.SINGLE_VARIABLE_DECLARATION: {
                SingleVariableDeclaration sv = (SingleVariableDeclaration) node
                        .getParent();
                ASTNode relevantParent = node.getRoot();
                if (relevantParent.getNodeType() == ASTNode.METHOD_DECLARATION) {
                    MethodDeclaration md = (MethodDeclaration) relevantParent;
                    System.out.println(
                            "\tSVMD: " + node.resolveTypeBinding().getQualifiedName()
                            + "" + md.getName()
                                    .getIdentifier()
                            + "" + sv.getName()
                                    .getIdentifier());
                }
                break;
            }
            default:
                break;
        }
    }

    private IMethod getIMethod(MethodDeclaration md) {
        IJavaElement javaElementA = md.resolveBinding().getJavaElement();
        IMethod iMethodA = null;
        if (javaElementA instanceof IMethod) {
            iMethodA = (IMethod) javaElementA;
        }
        return iMethodA;
    }

}
