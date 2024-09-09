package ifpb.gpes.filter;

import ifpb.gpes.Call;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The {@code FilterByMethod} class implements the {@link Predicate} interface to
 * filter method calls based on their names. The names are loaded from a
 * configuration file that contains a list of JCF (Java Collections Framework)
 * methods that modify object states.
 */
public class FilterByMethod implements Predicate<Call> {

    private List<String> nameList;

    public FilterByMethod() {
        this.populateNameList();
    }

    /**
     * Populates {@link FilterByMethod#nameList} with a list of names from JCF collections
     * methods that alter objects states.
     */
    private void populateNameList() {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("methods.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        this.nameList = reader.lines()
            .map(s -> Arrays.asList(s.replace(" ", "").split(",")))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

    /**
     * Test {@link Call} method names.
     * @param t The method call representation {@link Call}.
     * @return {@code true} if the method name from this call is contained in
     * {@link FilterByMethod#nameList}, {@code false} otherwise.
     */
    @Override
    public boolean test(Call t) {
        return nameList.contains(t.getMethodName().split("\\[")[0]);
    }
}
