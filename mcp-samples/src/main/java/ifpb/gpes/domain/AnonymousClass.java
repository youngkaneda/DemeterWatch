package ifpb.gpes.domain;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 03/07/2017, 21:38:08
 */
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

//    public void m4() {
//        Interface seg = () -> new HasJCFObject().getElements().iterator();
//        int a = 1;
//        m5(new HasJCFObject().getElements());
//    }

//    private void m5(List<A> lista) {
//        HasJCFObject value = lista.get(0);
//        value.getElements().listIterator();
//        lista.get(1).getElements().hashCode();
//    }
}
