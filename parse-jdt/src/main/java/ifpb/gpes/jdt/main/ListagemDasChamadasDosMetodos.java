package ifpb.gpes.jdt.main;

import ifpb.gpes.jdt.SmartASTParser;
import ifpb.gpes.jdt.MethodDeclarationVisitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// JDR
public class ListagemDasChamadasDosMetodos {

    static MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();

    public static void main(String[] args) {
        String sources = "/Users/job/Documents/dev/gpes/parse-review/parse-jdt/src/main/java/";
        String path = sources + "ifpb/gpes/jdt/samples";

        SmartASTParser smart = SmartASTParser.from(sources);
        readFilesInPath(Paths.get(path), smart);

        visitor.methodsCallFilter()
                .forEach(n -> System.out.println(n.callGraph()));
    }

    private static void readFilesInPath(Path get, SmartASTParser parser) {
        try {
            Files.list(get).forEach((t) -> {
                if (Files.isDirectory(t)) {
                    readFilesInPath(t, parser);
                } else {
                    if (isFileJava(t)) {
                        System.out.println("path: " + t);
                        parser.updateUnitName(t);
                        parser.acceptVisitor(visitor);
                    }
                }
            });
        } catch (IOException ex) {
        }
    }

    public static boolean isFileJava(Path file) {
        //TODO: poderia ser return file.endsWith(".java");
        String uri = file.getFileName().toString();
        String[] uriValues = uri.split("\\.");

        boolean verificacao = false;

        if (uriValues[uriValues.length - 1].equals("java")) {
            verificacao = true;
        }

        return verificacao;
    }

}


//<
//    ifpb.gpes.jdt.samples.A, // nome da classe totalmente qualificado
//    getElements, // método invocado
//    java.util.List<ifpb.gpes.jdt.samples.A>, // tipo do retorno do método invocado 
//    ifpb.gpes.jdt.samples.X,  //nome da classe cliente, totalmente qualificado
//    m2[String,String], // método que realiza a chamada do método
//    remove //método invocado 
// >
//public void clone(String url){
//    Git git = Git("https://github.com/ifpb-disciplinas-2017-1").clone();
//    Extract extract = new Extract(git);
//    Json json = extract.json();
//    return json;
//}