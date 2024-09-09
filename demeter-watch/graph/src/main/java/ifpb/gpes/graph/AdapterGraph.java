package ifpb.gpes.graph;

import ifpb.gpes.Call;
import java.util.List;
import java.util.function.Function;

/**
 * Adapter class that converts a list of {@code Call} objects into a {@code Graph}.
 * <p>
 * This class implements the {@code Function} interface and provides a way to transform
 * a list of method calls into a directed graph representation.
 * </p>
 */
public class AdapterGraph implements Function<List<Call>, Graph> {

    /**
     * Applies the transformation from a list of {@code Call} objects to a {@code Graph}.
     *
     * @param calls the list of {@code Call} objects representing method calls.
     * @return a {@code Graph} representation of the method calls.
     */
    @Override
    public Graph apply(List<Call> calls) {
        return calls.stream().collect(DefaultDirectGraph::new, DefaultDirectGraph::buildNode, (t, u) -> {});
    }
}
