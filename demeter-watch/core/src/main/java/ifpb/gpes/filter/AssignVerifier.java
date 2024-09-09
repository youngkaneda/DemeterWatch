package ifpb.gpes.filter;

import java.util.HashMap;

/**
 * The {@code AssignVerifier} class provides a mechanism to verify if a class
 * is assignable to a specified base class, considering Java's inheritance
 * rules. It also handles the case of array types and primitive types.
 */
public class AssignVerifier {

    private HashMap primitiveMap;
    private String baseClass;

    /**
     * Constructor that accepts a base class name to be used in comparisons.
     * @param baseClass Name of the class to be used as base class for assignable checks.
     */
    public AssignVerifier(String baseClass) {
        this.baseClass = baseClass;
        populatePrimitives();
    }

    /**
     * Check if a class is assignable to {@link AssignVerifier#baseClass}.
     * @param className The class name to be checked.
     * @return if the class passed as param is the same <code>Class</code> or a subclass of {@link AssignVerifier#baseClass}.
     */
    public boolean isAssignable(String className) {
        if (className.contains("[]")) {
            String replacedClass = className.replaceAll("\\[]", "");
            return isAssignable(replacedClass);
        }
        if (primitiveMap.containsKey(className)) {
            return false;
        }
        try {
            Class baseClass = Class.forName(this.baseClass);
            Class classToCompare = Class.forName(className);
            return baseClass.isAssignableFrom(classToCompare);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Populates a map of primitive classes to be used during {@link AssignVerifier#isAssignable(String)} checks.
     */
    private void populatePrimitives() {
        primitiveMap = new HashMap<String, Object>();
        primitiveMap.put("int", int.class);
        primitiveMap.put("long", long.class);
        primitiveMap.put("double", double.class);
        primitiveMap.put("char", char.class);
        primitiveMap.put("short", short.class);
        primitiveMap.put("byte", byte.class);
        primitiveMap.put("boolean", boolean.class);
        primitiveMap.put("void", void.class);
    }
}