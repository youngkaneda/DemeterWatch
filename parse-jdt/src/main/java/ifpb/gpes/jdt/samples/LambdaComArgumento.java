package ifpb.gpes.jdt.samples;

import java.util.function.Consumer;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 03/07/2017, 09:50:52
 */
public class LambdaComArgumento {

    public void m1() {
        new A().getElements()
                .forEach(new Consumer<A>() {
                    @Override
                    public void accept(A ts) {
                        ts.m6(ts).negate();
                    }
                });
    }
    public void m2() {
        new A().getElements()
                .forEach((A ts) -> {
                    ts.m6(ts).negate();
        });
    }
}
