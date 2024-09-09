package ifpb.gpes.domain;

import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;

public class LambdaWithArguments {

    public void m1() {
        new HasJCFObject().getElements()
            .forEach(new Consumer<HasJCFObject>() {
                @Override
                public void accept(HasJCFObject ts) {
                    ts.m6(ts).negate();
                }
            });
        m4().add(1);
    }

    public void m2() {
        new HasJCFObject().getElements()
            .forEach((HasJCFObject t) -> {
                t.m6(t).negate();
            });
    }

    public void m3() {
        HasJCFObject a = new HasJCFObject();
        Object[] mud = a.getElements().toArray();
        a.getElements()
            .stream()
            .forEach(t -> System.out.println(t.getElements().add(null)));
    }

    public Set<Integer> m4() {
        return new TreeSet<>();
    }
}
