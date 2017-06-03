package ifpb.gpes.jdt;

import ifpb.gpes.jdt.main.ListagemDasChamadasDosMetodos;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 02/06/2017, 14:49:57
 */
//TODO: alterar nome da classe
public class SmartASTParser {

    private final ASTParser parser = ASTParser.newParser(AST.JLS8);
    private final String[] sources;
    private final String[] classpath = {System.getProperty("java.home") + "/lib/rt.jar"};

    public static SmartASTParser createParse(String[] sources) {

        if (sources == null) {
            throw new IllegalArgumentException("sources is null");
        }

        return new SmartASTParser(sources);
    }

    public static SmartASTParser from(String... sources) {

        if (sources == null) {
            throw new IllegalArgumentException("path is null");
        }

        return new SmartASTParser(sources);
    }

    private SmartASTParser(String... sources) {
        this.sources = sources;
        this.parser.setKind(ASTParser.K_COMPILATION_UNIT);
        this.parser.setBindingsRecovery(true);
        this.parser.setCompilerOptions(JavaCore.getOptions());
    }

    public void updateUnitName(Path fileJava) {
        try {
            byte[] readAllBytes = Files.readAllBytes(fileJava);
            String str = new String(readAllBytes);
            this.parser.setResolveBindings(true);
            this.parser.setUnitName(fileJava.getFileName().toString());
            this.parser.setEnvironment(classpath, sources, new String[]{"UTF-8"}, true);
            this.parser.setSource(str.toCharArray());
        } catch (IOException ex) {
            Logger.getLogger(ListagemDasChamadasDosMetodos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void acceptVisitor(ASTVisitor visitor) {
        ASTNode createAST = parser.createAST(null);
        if (createAST.getAST().hasBindingsRecovery()) {
            Logger.getLogger(ListagemDasChamadasDosMetodos.class.getName()).log(Level.INFO, "Binding activated.");
        }
        createAST.accept(visitor);
    }
}
