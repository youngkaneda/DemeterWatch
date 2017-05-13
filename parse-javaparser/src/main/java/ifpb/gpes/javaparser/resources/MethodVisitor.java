package ifpb.gpes.javaparser.resources;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodVisitor extends VoidVisitorAdapter<Void>{

    @Override
    public void visit(MethodDeclaration n, Void arg){
        System.out.println(n.getType()+ " " + n.getName() + "\n");
        super.visit(n, arg);
    }
    
}
