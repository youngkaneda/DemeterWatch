package ifpb.gpes.javaparser.main;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import java.io.FileInputStream;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 09/05/2017, 10:55:34
 */
public class ListagemTodosMetodosDaClasse {
    public static void main(String[] args) throws Exception {
        FileInputStream in = new FileInputStream("/Users/job/Documents/dev/gpes/parse-review/parse-javaparser/src/main/java/ifpb/gpes/javaparser/samples/A.java");
        CompilationUnit cu = JavaParser.parse(in);
        System.out.println(cu.toString());
    }
}
