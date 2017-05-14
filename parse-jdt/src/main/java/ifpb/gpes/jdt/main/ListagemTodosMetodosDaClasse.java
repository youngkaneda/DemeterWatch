package ifpb.gpes.jdt.main;

import ifpb.gpes.jdt.MyVisitor;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
public class ListagemTodosMetodosDaClasse {

    public static void main(String[] args) {

        ASTParser parser = ASTParser.newParser(AST.JLS8);
        String sourceDir = "C:/Users/Juan/Documents/NetBeansProjects/multiBds/atividade/src";

        try {
            getFilePath(new File(sourceDir), parser);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void getFilePath(File file, ASTParser parser) throws IOException {
        if (file.isDirectory()) {
            for (File listFile : file.listFiles()) {
                getFilePath(listFile, parser);
            }
        } else {
            showMethods(file, parser);
        }
    }

    private static void showMethods(File file, ASTParser parser) throws IOException {

        setConfig(file.getName(), parser);

        MyVisitor visitor = new MyVisitor();

        byte[] readBytes = Files.readAllBytes(file.toPath());
        String str = new String(readBytes);

        parser.setSource(str.toCharArray());

        CompilationUnit cu = (CompilationUnit) parser.createAST(null);

        if (cu.getAST().hasBindingsRecovery()) {
            System.out.println("Binding activated.");
        }

        System.out.println("_" + file.getName() + "_\n");
        cu.accept(visitor);

    }

    public static void setConfig(String name, ASTParser parser) {
        parser.setKind(ASTParser.K_COMPILATION_UNIT);

        parser.setResolveBindings(true);
        parser.setBindingsRecovery(true);

        Map options = JavaCore.getOptions();
        parser.setCompilerOptions(options);

        parser.setUnitName(name);
        String[] sources = {"C:/Users/Juan/Documents/NetBeansProjects/multiBds/atividade/src/main/java/"};
        String[] classpath = {System.getProperty("java.home") + "/lib/rt.jar"};

        parser.setEnvironment(classpath, sources, new String[]{"UTF-8"}, true);
    }
}
