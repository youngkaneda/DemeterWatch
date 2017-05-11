package ifpb.gpes.javaparser.resources;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodVisitor extends VoidVisitorAdapter<Void>{
    
    @Override
    public void visit(MethodDeclaration n, Void arg){
        //gambiarra? talvez
        String[] values = n.getDeclarationAsString(false, false).split(" ");
        System.out.println(values[0] + " " + n.getName() + "\n");
        
        super.visit(n, arg);
    }
    
}
