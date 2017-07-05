package ifpb.gpes.jdt.samples;

import java.util.List;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 03/07/2017, 21:38:08
 */
public class ClasseAnonima {

    public void m1() {
        D first = new D() {
            public void m2() {
                new A().getElements().add(null);
                D second = new D() {
                    public void m3() {
                        A a = new A();
                        a.getElements().remove(null);
                        a.getElements().isEmpty();
                    }
                };
                new A().getElements().iterator();
                new A().getElements().listIterator();
            }
        };
        I seg = () -> new A().getElements().iterator();
        new A().getElements().hashCode();
    }

//    public void m4() {
//        I seg = () -> new A().getElements().iterator();
//        int a = 1;
//        m5(new A().getElements());
//    }

//    private void m5(List<A> lista) {
//        A value = lista.get(0);
//        value.getElements().listIterator();
//        lista.get(1).getElements().hashCode();
//    }
}
