package ifpb.gpes.jdt.samples;

import java.util.function.Consumer;

public class ExemploComMethodReference {

    public void chamadaComReference() {
        A a = new A();
        a.getElements().forEach(System.out::println);
//        a.getElements().forEach((x)->System.out.println(x));
    }

    public static void m1(A a) {
        new A().getElements().size();
    }

}
