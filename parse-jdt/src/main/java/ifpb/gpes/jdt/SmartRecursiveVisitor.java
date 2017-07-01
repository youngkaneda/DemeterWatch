package ifpb.gpes.jdt;

import ifpb.gpes.No;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionMethodReference;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.MethodRef;
import org.eclipse.jdt.core.dom.MethodRefParameter;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class SmartRecursiveVisitor extends ASTVisitor {

    private List<No> ns = new ArrayList<>();

    @Override
    public boolean visit(MethodDeclaration md) {

        if (md.isConstructor()) {
            return super.visit(md);
        }

        Block block = md.getBody();

        if (block == null) {
            return super.visit(md);
        }

        recursiveBlock(block.statements(), md);

        return super.visit(md);
    }

    @Override
    public boolean visit(ExpressionMethodReference node) {
        System.out.println("ExpressionMethodReference");
        System.out.println("-----");
        System.out.println(node);
        System.out.println("-----");
        return super.visit(node); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
//    public boolean visit(ExpressionStatement node) {
//        System.out.println("ExpressionStatement");
//        System.out.println("\t-----");
//        System.out.println(node);
//        
//        if (node.getExpression() instanceof MethodInvocation) {
//            MethodInvocation mi = (MethodInvocation) node.getExpression();
//            List argumentos = mi.arguments();
//            if (argumentos != null && !argumentos.isEmpty()) {
////                        recursiveBlock(argumentos, md);
//                argumentos.stream()
//                        .filter((argumento) -> (argumento instanceof LambdaExpression))
//                        .forEach((argumento) -> {
//                            LambdaExpression lambda = (LambdaExpression) argumento;
//                             
//
//                            int nodeType = lambda.getBody().getNodeType();
//                            if (nodeType == ASTNode.METHOD_INVOCATION) {
//                                MethodInvocation mit = (MethodInvocation) lambda.getBody();
//                                System.out.print(" mi - " + mit.getName().getFullyQualifiedName());
//                                System.out.print(" mi - " + mit.typeArguments());
//                                System.out.print(" mi - " + mit.resolveConstantExpressionValue());
//                                System.out.print(" mi - " + mit.resolveMethodBinding());
//                                System.out.print(" mi - " + mit.resolveTypeBinding());
//                                System.out.print(" mi - " + mit.getExpression());
//                                System.out.print(" mi - " + mit.getParent());
//                                System.out.println(" ----- ");
//                            }
//
//                            System.out.print(lambda.parameters());
//                            System.out.print(" - " + node.getExpression());
//                            System.out.print(" - " + node.properties());
//                            System.out.print(" - " + node.getExpression().resolveTypeBinding());
//                            System.out.print(" - " + mi.getName().getFullyQualifiedName());
////                            System.out.print(" - " + md.getName().getFullyQualifiedName());
//                            System.out.print(" - " + mi.arguments());
//                            System.out.println(" - " + argumento);
////                                    lambdaExpression(lambda);
//                        });
//
//            }
//        }
//        
//        System.out.println("\t----- ");
//        return super.visit(node); //To change body of generated methods, choose Tools | Templates.
//    }

    @Override
    public void endVisit(MethodRef node) {
        System.out.println("MethodRef");
        System.out.println("-----");
        System.out.println(node);
        System.out.println("-----");
        super.endVisit(node); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void endVisit(MethodRefParameter node) {
        System.out.println("MethodRefParameter");
        System.out.println("-----");
        System.out.println(node);
        System.out.println("-----");
        super.endVisit(node); //To change body of generated methods, choose Tools | Templates.
    }

    private void recursiveBlock(List statements, MethodDeclaration md) {
        for (Object state : statements) {
            if (state instanceof VariableDeclarationStatement) {
                VariableDeclarationStatement vds = (VariableDeclarationStatement) state;
                vds.fragments().forEach((frag) -> {
                    //o fragmento é um VariableDeclarationFragment ?
                    if (frag instanceof VariableDeclarationFragment) {
                        VariableDeclarationFragment vdf = (VariableDeclarationFragment) frag;
                        Expression initializer = vdf.getInitializer();
                        //o initializer é um ClassInstanceCreation ?
                        if (initializer instanceof ClassInstanceCreation) {
                            ClassInstanceCreation cic = (ClassInstanceCreation) vdf.getInitializer();
                            //pegar a referencia da declaracao da classe anonima
                            if (cic.getAnonymousClassDeclaration() != null) {
                                cic.getAnonymousClassDeclaration().accept(new SmartRecursiveVisitor());
                            }
                            //instancia um novo visitor desse pq n to conseguindo organizar um jeito
                            //legal de deixar tudo no metodo e ele ficar recursivo ate chegar no if
                            //do instanceof ExpressionStatement
                        }
                        //o initializer é uma LambdaExpression ?
                        //n consigo pensar num jeito de deixar esse recursivo tbm pq
                        //em interfaces ou pelo menos em lambda n funciona no mesmo processo
                        //de methodDeclaration -> methodInvocation
                        //a partir do body do lambda os proximos objetos sao expressioStatements
                        if (initializer instanceof LambdaExpression) {
                            lambdaExpression((LambdaExpression) vdf.getInitializer());
                        }
                        if (initializer instanceof MethodInvocation) {
                            MethodInvocation mi = (MethodInvocation) vdf.getInitializer();
//                            List argumentos = mi.arguments();
//                            if (argumentos != null && !argumentos.isEmpty()) {
//                                recursiveBlock(argumentos, md);
//                            }
//                            List argumentos = mi.arguments();
//                            if (argumentos != null && !argumentos.isEmpty()) {
//                                System.out.print(mi.getName().getFullyQualifiedName());
//                                System.out.println(" - " + md.getName().getFullyQualifiedName() + "->" + argumentos);
//                            }

                            mi.accept(new SmartBlockVisitor(md, ns));
                        }
                    }
                });
            }
            if (state instanceof ExpressionStatement) {
                ExpressionStatement es = (ExpressionStatement) state;

                if (es.getExpression() instanceof MethodInvocation) {
                    MethodInvocation mi = (MethodInvocation) es.getExpression();
                    List argumentos = mi.arguments();
                    if (argumentos != null && !argumentos.isEmpty()) {
//                        recursiveBlock(argumentos, md);
                        argumentos.stream()
                                .filter((argumento) -> (argumento instanceof LambdaExpression))
                                .forEach((argumento) -> {
                                    LambdaExpression lambda = (LambdaExpression) argumento;
//                                    lambda.getBody();
//
//                                    int nodeType = lambda.getBody().getNodeType();
//                                    if (nodeType == ASTNode.METHOD_INVOCATION) {
//                                        MethodInvocation mit = (MethodInvocation) lambda.getBody();
//                                        System.out.print(" mi - " + mit.getName().getFullyQualifiedName());
//                                        System.out.print(" mi - " + mit.typeArguments());
//                                        System.out.print(" mi - " + mit.resolveConstantExpressionValue());
//                                        System.out.print(" mi - " + mit.resolveMethodBinding());
//                                        System.out.print(" mi - " + mit.resolveTypeBinding());
//                                        System.out.print(" mi - " + mit.getExpression());
//                                        System.out.println(" ----- ");
//                                    }
//
//                                    System.out.print(lambda.parameters());
//                                    System.out.print(" - " + es.getExpression());
//                                    System.out.print(" - " + es.properties());
//                                    System.out.print(" - " + es.getExpression().resolveTypeBinding());
//                                    System.out.print(" - " + mi.getName().getFullyQualifiedName());
//                                    System.out.print(" - " + md.getName().getFullyQualifiedName());
//                                    System.out.print(" - " + mi.arguments());
//                                    System.out.println(" - " + argumento);
                                    lambdaExpression(lambda);
                                });

                    }
                }
                es.accept(new SmartBlockVisitor(md, ns));
            }
        }
    }

    private void lambdaExpression(LambdaExpression le) {
        //sinto que passar o proprio objeto como parametro é muito errado
        int nodeType = le.getBody().getNodeType();
        if (nodeType == ASTNode.METHOD_INVOCATION) {
            le.accept(new SmartLambdaMIVisitor(ns, le));

//                                MethodInvocation mi = (MethodInvocation) le.getBody();
//                                List argumentos = mi.arguments();
//                                if (argumentos != null && !argumentos.isEmpty()) {
//                                    System.out.print(mi.getName().getFullyQualifiedName());
//                                    System.out.println(" - " + md.getName().getFullyQualifiedName() + "->" + argumentos);
//                                }
        } else {
            le.accept(new SmartLambdaVisitor(ns, le));
        }
    }

    public List<No> methodsCall() {
        return Collections
                .unmodifiableList(ns.stream().collect(Collectors.toList()));
    }

}
