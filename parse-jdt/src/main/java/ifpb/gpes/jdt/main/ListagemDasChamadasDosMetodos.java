package ifpb.gpes.jdt.main;

import ifpb.gpes.jdt.MyAnotherVisitor;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

// JDR

public class ListagemDasChamadasDosMetodos {

    public static void main(String[] args) {
//        String path = "/Users/job/Documents/dev/gpes/parse-review/parse-jdt/src/main/java/ifpb/gpes/jdt/samples";
        String path = "/home/juan/facul/periodo4/projetoDePesquisa/parse/alvo/sigeve/src/main/java/";
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        readFilesInPath(Paths.get(path), parser);
    }

    private static void walkFileTreeInPath(Path get, ASTParser parser) {
        try {
            Files.walkFileTree(get, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    showMethods(file, parser);
                    return super.visitFile(file, attrs);
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(ListagemDasChamadasDosMetodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean extensionVerification(Path file) {
        String uri = file.getFileName().toString();
        String[] uriValues = uri.split("\\.");

        boolean verificacao = false;

        if (uriValues[uriValues.length - 1].equals("java")) {
            verificacao = true;
        }

        return verificacao;
    }

    private static void readFilesInPath(Path get, ASTParser parser) {
        try {
            Files.list(get).forEach((t) -> {
                if (Files.isDirectory(t)) {
                    readFilesInPath(t, parser);
                } else {
                    if (extensionVerification(t)) {
                        showMethods(t, parser);
                    }
                }
            });
        } catch (IOException ex) {
        }
    }

    private static void showMethods(Path path, ASTParser parser) {

        try {
            MyAnotherVisitor visitor = new MyAnotherVisitor();
            byte[] readAllBytes = Files.readAllBytes(path);
            String str = new String(readAllBytes);

            parser.setResolveBindings(true);
            parser.setKind(ASTParser.K_COMPILATION_UNIT);

            parser.setBindingsRecovery(true);

            Map options = JavaCore.getOptions();
            parser.setCompilerOptions(options);
            parser.setUnitName(path.getFileName().toString());

//            String[] sources = {"/Users/job/Documents/dev/gpes/parse-review/parse-jdt/src/main/java/"};
            String[] sources = {"/home/juan/facul/periodo4/projetoDePesquisa/parse/alvo/sigeve/"
                    + "src/main/java/"};
            String[] classpath = {System.getProperty("java.home") + "/lib/rt.jar"};
            parser.setEnvironment(classpath, sources, new String[]{"UTF-8"}, true);
            parser.setSource(str.toCharArray());
            CompilationUnit cut = (CompilationUnit) parser.createAST(null);
            if (cut.getAST().hasBindingsRecovery()) {
                System.out.println("Binding activated.");
            }
            cut.accept(visitor);
        } catch (IOException ex) {
            Logger.getLogger(ListagemDasChamadasDosMetodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
