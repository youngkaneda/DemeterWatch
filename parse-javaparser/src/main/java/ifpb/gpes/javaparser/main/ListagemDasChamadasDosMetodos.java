package ifpb.gpes.javaparser.main;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import ifpb.gpes.javaparser.resources.MethodCallVisitor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 09/05/2017, 10:55:34
 */
//see: https://github.com/javaparser/javaparser/wiki/Manual
public class ListagemDasChamadasDosMetodos {

    public static void main(String[] args) {

        File file = new File("C:/Users/Juan/Documents/NetBeansProjects/multiBds/atividade/src");
        try {
            percorrePacotes(file);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static void percorrePacotes(File file) throws FileNotFoundException {

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                percorrePacotes(file1);
            }
        } else {
            showCallMethods(file.getAbsoluteFile());
        }

    }

    public static void showCallMethods(File file) throws FileNotFoundException {

        FileInputStream in = new FileInputStream(file);
        CompilationUnit cu = JavaParser.parse(in);
        
        System.out.println("* " + file.getName() + " *");
        
        MethodCallVisitor mv = new MethodCallVisitor();
        mv.visit(cu, null);

    }
}
