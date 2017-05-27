package ifpb.gpes.jdt.samples;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 26/05/2017, 21:32:40
 */
public class D {
    private A a = new A();
    public void teste() {
        this.a.getElements().add(new A());
    }
}
