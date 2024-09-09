package ifpb.gpes.domain;

import java.util.List;
import java.util.function.Predicate;

public class HasJCFObject {

    private List<HasJCFObject> elements;

    public List<HasJCFObject> getElements() {
        return this.elements;
    }

    public Predicate<HasJCFObject> m6(HasJCFObject a) {

        return new Predicate<HasJCFObject>() {
            @Override
            public boolean test(HasJCFObject t) {
                return false;
            }
        };
    }
}
