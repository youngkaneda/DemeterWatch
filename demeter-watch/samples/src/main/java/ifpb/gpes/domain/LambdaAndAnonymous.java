package ifpb.gpes.domain;

import java.util.List;

public class LambdaAndAnonymous {

    private SampleObject d = new SampleObject();
    private Interface i;

    public void testOther() {
        this.d.test();
    }

    public void m3() {
        i.noReturn();
        Interface seg = () -> new HasJCFObject().getElements().iterator();
        AbstractClass b = new AbstractClass() {
            @Override
            public String m5() {
                new HasJCFObject().getElements()
                    .stream()
                    .forEach((HasJCFObject ts) -> {
                        ts.m6(ts).negate();
                        ts.m6(ts).hashCode();
                    });
                return "2";
            }
        };
        HasJCFObject a = new HasJCFObject();
        list(a.getElements());
    }

    private void list(List<HasJCFObject> collection) {
        collection.get(0);
    }
}
