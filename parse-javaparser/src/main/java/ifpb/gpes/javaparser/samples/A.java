package ifpb.gpes.javaparser.samples;

import java.util.List;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 19/08/2016, 17:34:16
 */
public class A{
    private List<A> elements;
    public List<A> getElements(){
        elements.add(this);
        return this.elements;
    }
}
