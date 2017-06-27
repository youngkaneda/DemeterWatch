package ifpb.gpes.jdt;

import ifpb.gpes.No;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
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

    private void recursiveBlock(List statements, MethodDeclaration md) {
        for (Object state : statements) {
            if (state instanceof VariableDeclarationStatement) {
                VariableDeclarationStatement vds = (VariableDeclarationStatement) state;
                vds.fragments().forEach((frag) -> {
                    //o fragmento é um VariableDeclarationFragment ?
                    if (frag instanceof VariableDeclarationFragment) {
                        VariableDeclarationFragment vdf = (VariableDeclarationFragment) frag;
                        //o initializer é um ClassInstanceCreation ?
                        if (vdf.getInitializer() instanceof ClassInstanceCreation) {
                            ClassInstanceCreation cic = (ClassInstanceCreation) vdf.getInitializer();
                            //pegar a referencia da declaracao da classe anonima
                            cic.getAnonymousClassDeclaration().accept(new SmartRecursiveVisitor());
                            //instancia um novo visitor desse pq n to conseguindo organizar um jeito
                            //legal de deixar tudo no metodo e ele ficar recursivo ate chegar no if
                            //do instanceof ExpressionStatement
                        }
                        //o initializer é uma LambdaExpression ?
                        //n consigo pensar num jeito de deixar esse recursivo tbm pq
                        //em interfaces ou pelo menos em lambda n funciona no mesmo processo
                        //de methodDeclaration -> methodInvocation
                        //a partir do body do lambda os proximos objetos sao expressioStatements
                        if (vdf.getInitializer() instanceof LambdaExpression) {
                            Expression le = (LambdaExpression) vdf.getInitializer();
                            //sinto que passar o proprio objeto como parametro é muito errado
                            le.accept(new SmartLambdaVisitor(ns, le));
                        }
                    }
                });
            }
            if (state instanceof ExpressionStatement) {
                ExpressionStatement es = (ExpressionStatement) state;
                es.accept(new SmartBlockVisitor(md, ns));
            }
        }
    }

    public List<No> methodsCall() {
        return Collections
                .unmodifiableList(ns.stream().collect(Collectors.toList()));
    }

}
