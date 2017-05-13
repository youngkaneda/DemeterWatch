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

    public static void percorrePacotes(File file) throws FileNotFoundException {

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                percorrePacotes(file1);
            }
        } else {
            showClassMethods(file.getAbsoluteFile());
        }

    }

    public static void showClassMethods(File file) throws FileNotFoundException {

        FileInputStream in = new FileInputStream(file);
        CompilationUnit cu = JavaParser.parse(in);

//      ocorre um erro de runtime dizendo q a pasta ScrDir n eh um diretorio ou n esta localizado na pasta com as classe
//      eu tentei imaginar oq tivesse causando esse erro mas foi mt vago
//      a documentacao n oferece nenhuma informacao sobre varios metodos inclusive o 'declaringType()' que eu gostaria de testar
//      ms devido ao erro n estou conseguindo

//        ReferenceTypeDeclaration rtd;
//
//        for (TypeDeclaration td : cu.getTypes()) {
//            rtd = JavaParserFacade.get(new JavaParserTypeSolver(file)).getTypeDeclaration(td);
//            
//            System.out.println(rtd.getQualifiedName());
//            
//            Iterator<MethodUsage> methods = rtd.getAllMethods().iterator();
//            
//            while (methods.hasNext()) {
//                System.out.println(methods.next().getQualifiedSignature()); ou
//                System.out.println(methods.next().declaringType().getQualifiedName());
//            }
//        
//        }
    
        MethodVisitor mv = new MethodVisitor();
        mv.visit(cu, null);

    }
}
