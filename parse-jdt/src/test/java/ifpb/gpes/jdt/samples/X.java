package ifpb.gpes.jdt.samples;

import java.util.List;

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

    public void m3() {
        i.semRetorno();
        I seg = () -> new A().getElements().iterator();
        B b = new B() {
            @Override
            public String m5() {
                new A().getElements()
                        .stream()
                        .forEach((A ts) -> {
                            ts.m6(ts).negate();
                        });
                return "2";
            }
        };
        A a = new A();
        listar(a.getElements());
    }

    private void listar(List<A> lista) {
        lista.get(0);
    }
}
