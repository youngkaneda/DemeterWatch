package ifpb.gpes.domain;

public class SampleObject {
    private HasJCFObject a = new HasJCFObject();

    public void test() {
        a.getElements().add(new HasJCFObject());
    }

    public HasJCFObject m1() {
        return this.a;
    }
}
