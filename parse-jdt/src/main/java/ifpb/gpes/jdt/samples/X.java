package ifpb.gpes.jdt.samples;

/**
 *
 * @author juan
 */
public class X {

    private D d = new D();

    public void testeOutro() {
        this.d.teste();
    }

    public void m2() {
        this.d.m1().getElements().remove(new A());
    }

    public void m2(String tst) {
        this.d.m1().getElements().remove(new A());
    }
    public void m2(String tst, Integer a) {
        this.d.m1().getElements().remove(new A());
    }
}
