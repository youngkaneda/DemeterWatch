package ifpb.gpes.jdt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

// JDR
//TODO atualizar o nome da classe
public class MyAnotherVisitor extends ASTVisitor {

    private final List<No> ns = new ArrayList<>();
    private int count = 0;

    @Override
    public boolean visit(MethodDeclaration md) {
        if (md.isConstructor()) {
            return super.visit(md);
        }
        Block block = md.getBody();

        if (block == null) {
            return super.visit(md);
        }
        block.accept(new BlockVisitor(md));

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

    class BlockVisitor extends ASTVisitor {

        private final MethodDeclaration md;

        private BlockVisitor(MethodDeclaration md) {
            this.md = md;
        }

        @Override
        public boolean visit(MethodInvocation mi) {

            No no = new No();

            String a = "SAD";
            String returnType = "SADNESS";

            IMethodBinding imb = mi.resolveMethodBinding();

            ITypeBinding[] bindings = {};
//            int length = 0;

            if (imb != null) {
                bindings = imb.getParameterTypes();
//                length = bindings.length;
                a = imb.getDeclaringClass().getBinaryName();
                returnType = imb.getReturnType().getQualifiedName();
            }

            no.setA(a);

            String m = fillMethodName(mi.getName().toString(), bindings);

            no.setM(m);

            no.setRt(returnType);

            String c = md.resolveBinding().getDeclaringClass().getQualifiedName();

            no.setC(c);

            bindings = md.resolveBinding().getParameterTypes();
//            length = bindings.length;

            String m1 = fillMethodName(md.getName().getIdentifier(), bindings);
            no.setM1(m1);

            Expression inv = mi.getExpression();

            if (inv != null) {
                String[] ms = inv.toString().split("\\.");
                int size = ms.length;
                no.setInv(ms[size - 1]);
            } else {
                no.setInv("nothing here");
            }

            ns.add(no);
            count++;

            String[] values = m.split("\\[");

            if (count > 1) {
                if (ns.get(count - 2).getInv().contains(values[0])) {
                    ns.get(count - 1).setMi(ns.get(count - 2).getM());
                }
            }
//            System.out.println("--");
//            ns.stream().forEach(System.out::println);

            return super.visit(mi);
        }

        private String fillMethodName(String methodName, ITypeBinding[] bindings) {
            return Arrays
                    .asList(bindings)
                    .stream()
                    .map(b -> b.getQualifiedName())
                    .collect(Collectors.joining(", ", methodName + "[", "]"));

//            StringBuilder m = new StringBuilder(methodName);
//            m.append("[");
//            for (int i = 0; i < length; i++) {
//                if (i < length - 1) {
//                    m.append(bindings[i].getQualifiedName()).append(",");
//                } else {
//                    m.append(bindings[i].getQualifiedName());
//                }
//            }
//            m.append("]");
//            
//            return m.toString();
        }
    }

}
