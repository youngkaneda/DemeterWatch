package ifpb.gpes.jdt.samples;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 19/08/2016, 17:34:16
 */
public class A {

    private List<A> elements;

    public List<A> getElements() {
        return this.elements;
    }

    public Predicate<A> m6(A a) {

        return (A t) -> false;

    }
}
