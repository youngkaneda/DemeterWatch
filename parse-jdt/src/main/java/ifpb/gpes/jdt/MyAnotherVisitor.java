package ifpb.gpes.jdt;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

// JDR

public class MyAnotherVisitor extends ASTVisitor {

    private No no;
    private static List<No> ns = new ArrayList<>();
    private static int count = 0;

    @Override
    public boolean visit(MethodDeclaration md) {
        if (!md.isConstructor()) {
            if (md.getBody() != null) {
                md.getBody().
                        accept(new ASTVisitor() {
                            @Override
                            public boolean visit(MethodInvocation mi) {

                                no = new No();

                                String a = "SAD";
                                String returnType = "SADNESS";

                                IMethodBinding imb = mi.resolveMethodBinding();

                                ITypeBinding[] bindings = {};
                                int length = 0;

                                if (imb != null) {
                                    bindings = imb.getParameterTypes();
                                    length = bindings.length;

                                    a = imb.getDeclaringClass().getBinaryName();
                                    returnType = imb.getReturnType().getQualifiedName();
                                }

                                no.setA(a);

                                StringBuilder m = new StringBuilder();
                                m.append(mi.getName().toString()).append("[");

                                for (int i = 0; i < length; i++) {
                                    if (i < length - 1) {
                                        m.append(bindings[i].getQualifiedName()).append(",");
                                    } else {
                                        m.append(bindings[i].getQualifiedName());
                                    }
                                }

                                m.append("]");

                                no.setM(m.toString());

                                no.setRt(returnType);

                                String c = md.resolveBinding().getDeclaringClass().getQualifiedName();

                                no.setC(c);

                                StringBuilder m1 = new StringBuilder();
                                m1 = m1.append(md.getName().getIdentifier()).append("[");

                                bindings = md.resolveBinding().getParameterTypes();
                                length = bindings.length;

                                for (int i = 0; i < length; i++) {
                                    if (i < length - 1) {
                                        m1.append(bindings[i].getBinaryName()).append(",");
                                    } else {
                                        m1.append(bindings[i].getBinaryName());
                                    }
                                }

                                m1.append("]");

                                no.setM1(m1.toString());

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

                                String[] values = m.toString().split("\\[");

                                if (count > 1) {
                                    if (ns.get(count - 2).getInv().contains(values[0])) {
                                        ns.get(count - 1).setMi(ns.get(count - 2).getM());
                                    }
                                }

                                System.out.println("--");
                                ns.stream().forEach(System.out::println);

                                return super.visit(mi);
                            }
                        });
            }
        }
        return super.visit(md);
    }

}
