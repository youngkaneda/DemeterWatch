package ifpb.gpes.main;
//package br.edu.ifpb.scm.api.git

import ifpb.gpes.io.SmartFile;
import ifpb.gpes.jdt.SmartMethodVisitor;
import ifpb.gpes.jdt.SmartASTParser;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 02/06/2017, 16:41:58
 */
public class Start {

    static SmartMethodVisitor visitor = new SmartMethodVisitor();

    public static void main(String[] args) {

        String sources = "/Users/job/Documents/dev/gpes/parse-review/parse-jdt/src/main/java/";
        String path = sources + "ifpb/gpes/jdt/samples";

        SmartFile smart = SmartFile.from(Paths.get(path));
        SmartASTParser parser = SmartASTParser.from(sources);
        
        Stream<Path> files = smart.extension(".java");
        
        files.forEach(p -> {
            parser.updateUnitName(p);
            parser.acceptVisitor(visitor);
        });

        visitor.methodsCallFilter()
                .forEach(n -> System.out.println(n.callGraph()));
    }

}
