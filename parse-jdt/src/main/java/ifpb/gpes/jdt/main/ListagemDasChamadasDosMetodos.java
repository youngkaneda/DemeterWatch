package ifpb.gpes.jdt.main;

import ifpb.gpes.jdt.ListASTVisitor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 09/05/2017, 10:55:34
 */
public class ListagemDasChamadasDosMetodos {

    public static void main(String[] args) throws IOException {
        String file = "C:/Users/Juan/Documents/NetBeansProjects/multiBds/atividade/src/main/java/com/mycompany/atividade/app/App.java";

        ListASTVisitor visitor = new ListASTVisitor();

        byte[] readAllBytes = Files.readAllBytes(Paths.get(file));
        String str = new String(readAllBytes);

        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setResolveBindings(true);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);

        parser.setBindingsRecovery(true);

        Map options = JavaCore.getOptions();
        parser.setCompilerOptions(options);

        String unitName = "App.java";
        parser.setUnitName(unitName);

        String[] sources = {"C:/Users/Juan/Documents/NetBeansProjects/multiBds/atividade/src/main/java/"};
        String[] classpath = {System.getProperty("java.home")+"/lib/rt.jar"};

        parser.setEnvironment( classpath, sources, new String[]{"UTF-8"}, true);
        parser.setSource(str.toCharArray());

        CompilationUnit cut = (CompilationUnit) parser.createAST(null);

        if (cut.getAST().hasBindingsRecovery()) {
            System.out.println("Binding activated.");
        }
        cut.accept(visitor);
    }
}
