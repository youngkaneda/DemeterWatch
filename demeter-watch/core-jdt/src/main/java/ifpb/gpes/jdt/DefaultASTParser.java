package ifpb.gpes.jdt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ifpb.gpes.io.SmartFile;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;

/**
 * A class for parsing Java source files using Eclipse JDT's AST parser.
 * Supports configuration of source paths and classpaths, and provides methods
 * to update the unit name and accept AST visitors for analyzing Java code.
 */
public class DefaultASTParser {

    private final ASTParser parser = ASTParser.newParser(AST.JLS17);
    private final String[] sources;
    private Hashtable<String, String> options = JavaCore.getOptions();
    private final String javaDefaultClasspath = System.getProperty("java.home") + "/lib/jrt-fs.jar";
    private final String[] classpath;

    /**
     * Creates a new instance of {@code DefaultASTParser} with specified source paths and default classpath.
     *
     * @param sources Array of source paths.
     * @return A new instance of {@code DefaultASTParser}.
     * @throws IllegalArgumentException if {@code sources} is {@code null}.
     */
    public static DefaultASTParser from(String... sources) {
        if (sources == null) {
            throw new IllegalArgumentException("sources is null");
        }
        return new DefaultASTParser(sources, null);
    }

    /**
     * Creates a new instance of {@code DefaultASTParser} with specified source paths and classpath.
     *
     * @param sources Array of source paths.
     * @param classpath Array of classpath entries.
     * @return A new instance of {@code DefaultASTParser}.
     * @throws IllegalArgumentException if {@code sources} is {@code null}.
     */
    public static DefaultASTParser from(String[] sources, String[] classpath) {
        if (sources == null) {
            throw new IllegalArgumentException("sources is null");
        }
        return new DefaultASTParser(sources, classpath);
    }

    /**
     * Private constructor for initializing {@code DefaultASTParser} with source paths and classpath.
     *
     * @param sources Array of source paths.
     * @param classpath Array of classpath entries.
     */
    private DefaultASTParser(String[] sources, String[] classpath) {
        this.sources = sources;
        if (classpath == null) {
            this.classpath = new String[]{javaDefaultClasspath};
        } else {
            List<String> jarsPathFromClasspath = getJarsPathFromClasspath(classpath);
            jarsPathFromClasspath.add(javaDefaultClasspath);
            this.classpath = jarsPathFromClasspath.toArray(String[]::new);
        }
        this.parser.setKind(ASTParser.K_COMPILATION_UNIT);
        options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_17);
        options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_17);
        options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_17);
    }

    /**
     * Updates the parser with the content of a Java file and sets the unit name for parsing.
     *
     * @param fileJava Path to the Java file.
     */
    public void updateUnitName(Path fileJava) {
        try {
            byte[] readAllBytes = Files.readAllBytes(fileJava);
            String str = new String(readAllBytes);
            this.parser.setBindingsRecovery(true);
            this.parser.setResolveBindings(true);
            this.parser.setUnitName(fileJava.getFileName().toString());
            this.parser.setEnvironment(classpath, sources, new String[]{"UTF-8"}, true);
            this.parser.setSource(str.toCharArray());
        } catch (IOException ex) {
            Logger.getLogger(DefaultASTParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Accepts an {@code ASTVisitor} to traverse and analyze the AST created from the parsed source.
     *
     * @param visitor An instance of {@code ASTVisitor} for analyzing the AST.
     */
    public void acceptVisitor(ASTVisitor visitor) {
        this.parser.setCompilerOptions(options);
        ASTNode createAST = parser.createAST(null);
        createAST.accept(visitor);
    }

    /**
     * Retrieves the paths of JAR files from the provided classpath entries.
     *
     * @param classpath Array of classpath entries.
     * @return A list of paths to JAR files.
     */
    private List<String> getJarsPathFromClasspath(String... classpath) {
        List<Path> paths = new ArrayList<>();
        for (String cp : classpath) {
            Stream<Path> stream = SmartFile.from(Paths.get(cp)).extension(".jar");
            paths.addAll(stream.collect(Collectors.toList()));
        }
        return paths.stream().map(p -> p.toAbsolutePath().normalize().toString()).collect(Collectors.toList());
    }
}
