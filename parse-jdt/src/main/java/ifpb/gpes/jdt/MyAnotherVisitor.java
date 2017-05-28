package ifpb.gpes.jdt;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

/**
 *
 * @author Juan
 */
public class MyAnotherVisitor extends ASTVisitor {

    private No n;
    private static List<No> ns = new ArrayList<>();
//    private static Set<No> ns = new HashSet<>();
    private static int count = 0;

    @Override
    public boolean visit(MethodDeclaration md) {
        md.getBody().
                accept(new ASTVisitor() {
                    @Override
                    public boolean visit(MethodInvocation mi) {

                        n = new No();

                        IMethodBinding imb = mi.resolveMethodBinding();

                        String a = mi.resolveMethodBinding().getDeclaringClass().getBinaryName();
                        n.setA(a);
                        String m = mi.getName().getIdentifier();
                        n.setM(m);
                        String returnType = imb.getReturnType().getQualifiedName();
                        n.setRt(returnType);
                        String c = md.resolveBinding().getDeclaringClass().getQualifiedName();
                        n.setC(c);
                        String m1 = md.getName().getIdentifier();
                        n.setM1(m1);
                        Expression inv = mi.getExpression();

                        String[] ms = inv.toString().split("\\.");
                        int size = ms.length;

                        n.setInv(ms[size - 1]);

                        ns.add(n);
                        count++;

                        if (count > 1) {
                            if (ns.get(count - 2).getInv().contains(m)) {
                                ns.get(count - 1).setMi(ns.get(count - 2).getM());
                            }
                        }

//                        System.out.println(ns.toString());
                        System.out.println("--");
                        ns.stream().filter(t -> t.getMi() != null).forEach(System.out::println);

                        return super.visit(mi);
                    }
                });

        return super.visit(md);
    }

}
