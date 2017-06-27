package ifpb.gpes.jdt.samples;

import java.util.function.Consumer;

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
        Runnable runner = () -> {
            new A().getElements().size();
            System.out.print("oi");
        };
        Consumer<String> consumer = (a) -> new A().getElements().equals(a);
        
        I seg = () -> {
            new A().getElements().iterator();
        };
    }
}
