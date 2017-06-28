package ifpb.gpes.jdt.samples;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 *
 * @author juan
 */
public class X {

    private D d = new D();
    private I i;

    public void testeOutro() {
        this.d.teste();
    }

//    public void m2() {
//        this.d.m1().getElements().remove(new A());
//    }
//    public void m2(String tst) {
//        this.d.m1().getElements().remove(new A());
//    }
//    public void m2(String tst, Integer a) {
//        this.d.m1().getElements().remove(new A());
//    }
    public int m2(X x) {
        return 1;
    }

    public void m3() {
        i.semRetorno();
        D d = new D() {
            public void testando() {
                System.out.println("oi");
                new A().getElements().add(null);
                D d2 = new D() {
                    public void m4() {
                        A a = new A();
                        a.getElements().remove(null);
                        a.getElements().isEmpty();
                    }
                };
            }
        };

        List lista = new A().getElements();

        Runnable runner = () -> {
            int size = new A().getElements().size();
            new A().getElements().contains(null);
            System.out.print("oi");
        };
        Consumer<String> consumer = (a) -> {
            List<A> lista1 = new A().getElements();
            lista.equals(a);
            lista.clear();
        };
        I seg = () -> new A().getElements().iterator();

        B b = new B() {
            @Override
            public String m5() {
                A a = new A();
                Object[] mud = a.getElements().toArray();
                a.getElements().stream().forEach(t -> t.m6(t));
                return "2";
            }
        };
    }

}
