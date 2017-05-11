package ifpb.gpes.javaparser.main;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import ifpb.gpes.javaparser.resources.MethodVisitor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ListagemTodosMetodosDaClasse {
    public static void main(String[] args) {
        
        File file = new File("C:/Users/Juan/Documents/NetBeansProjects/multiBds/atividade/src");
        try {
            percorrePacotes(file);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public static void percorrePacotes(File file) throws FileNotFoundException{
        
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for (File file1 : files) {
                percorrePacotes(file1);
            }
        }else
            showClassMethods(file);
    
    }
    
    public static void showClassMethods(File file) throws FileNotFoundException{

        FileInputStream in = new FileInputStream(file);
        CompilationUnit cu = JavaParser.parse(in);
     
        System.out.println("* " + file.getName() + " * \n");
        
        MethodVisitor mv = new MethodVisitor();
        mv.visit(cu, null);
        
    }    
}
