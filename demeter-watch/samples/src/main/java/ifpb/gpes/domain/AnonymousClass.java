package ifpb.gpes.domain;

public class AnonymousClass {

    public void m1() {
        SampleObject first = new SampleObject() {
            public void m2() {
                new HasJCFObject().getElements().add(null);
                SampleObject second = new SampleObject() {
                    public void m3() {
                        HasJCFObject a = new HasJCFObject();
                        a.getElements().remove(null);
                        a.getElements().isEmpty();
                    }
                };
                new HasJCFObject().getElements().iterator();
                new HasJCFObject().getElements().listIterator();
            }
        };
        Interface seg = () -> new HasJCFObject().getElements().iterator();
        new HasJCFObject().getElements().hashCode();
    }
}
