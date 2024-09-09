package ifpb.gpes.filter;

import ifpb.gpes.Call;

import java.util.function.Predicate;

/**
 * The {@code FilterClassType} class implements the {@link Predicate} interface
 * to filter method calls based on the class type of the {@link Call} object.
 * It uses an {@link AssignVerifier} to check if the class type is assignable
 * to a specified base class.
 */
public class FilterClassType implements Predicate<Call> {

    private AssignVerifier verifier;

    /**
     * Constructor that accepts a base class name to be used by {@link FilterClassType#verifier}.
     * @param baseClass Name of the class to be used as base class for assignable
     * checks during test.
     */
    public FilterClassType(String baseClass) {
        verifier = new AssignVerifier(baseClass);
    }

    /**
     * Test {@link Call} class type.
     * @param t The method call representation {@link Call}.
     * @return {@code true} if the method call representation is from <code>java.util</code>
     * package and isAssignable to {@link FilterClassType#verifier} baseClass, {@code false} otherwise.
     */
    @Override
    public boolean test(Call t) {
        if (t.getClassType() == null || !t.getClassType().contains("java.util.")) {
            return false;
        }
        return verifier.isAssignable(t.getClassType().split("<")[0]);
    }
}
