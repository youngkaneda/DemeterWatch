package ifpb.gpes.jdt.samples;

import java.util.function.Predicate;

public class MethodReferenceExample {

    public void chamadaComReference() {
        HasJCFObject a = new HasJCFObject();
        a.getElements().forEach(System.out::println);
//        a.getElements().forEach((x)->System.out.println(x));
    }

    public static void m1(Predicate<HasJCFObject> a) {
        new HasJCFObject().getElements().size();
    }

}
