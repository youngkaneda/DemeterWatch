package ifpb.gpes.jdt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.MethodDeclaration;

// JDR
//TODO atualizar o nome da classe
public class MethodDeclarationVisitor extends ASTVisitor {

    private final List<No> ns = new ArrayList<>();

    @Override
    public boolean visit(MethodDeclaration md) {
        if (md.isConstructor()) {
            return super.visit(md);
        }
        Block block = md.getBody();

        if (block == null) {
            return super.visit(md);
        }
        block.accept(new BlockVisitor(md, ns));

        return super.visit(md);
    }

    public List<No> methodsCall() {
        return Collections.unmodifiableList(ns);
    }

    public List<No> methodsCallFilter() {
        return Collections
                .unmodifiableList(ns.stream()
                        .filter(t -> t.getMi() != null)
                        .collect(Collectors.toList()));
    }
}
