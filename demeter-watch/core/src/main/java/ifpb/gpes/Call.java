package ifpb.gpes;

import ifpb.gpes.filter.AssignVerifier;

import java.util.Objects;

/**
 * Represents a method call and contains details such as the class type, method name, return type,
 * and information about where the method is called from. This class also supports parsing method calls
 * from a string and checking if the method belongs to a specific class.
 */
public class Call {

    private String classType;
    private String methodName;
    private String returnType;
    private String calledInClass;
    private String calledInMethod;
    private String calledInMethodReturnType;
    private String callMethod;
    private String invokedBy;

    public Call() {}

    /**
     * Constructs a {@code Call} instance with the provided attributes.
     *
     * @param classType The type of the class where the method is located.
     * @param methodName The name of the method being called.
     * @param returnType The return type of the method.
     * @param calledInClass The class in which the method is called.
     * @param calledInMethod The method in which the method is called.
     * @param calledInMethodReturnType The return type of the calling method.
     * @param callMethod The method that invokes te call.
     * @param invokedBy The entity that invokes the call.
     */
    public Call(String classType, String methodName, String returnType, String calledInClass, String calledInMethod, String calledInMethodReturnType, String callMethod, String invokedBy) {
        this.classType = classType;
        this.methodName = methodName;
        this.returnType = returnType;
        this.calledInClass = calledInClass;
        this.calledInMethod = calledInMethod;
        this.calledInMethodReturnType = calledInMethodReturnType;
        this.callMethod = callMethod;
        this.invokedBy = invokedBy;
    }

    /**
     * Static factory method for creating a {@code Call} instance.
     *
     * @param classType The type of the class where the method is located.
     * @param methodName The name of the method being called.
     * @param returnType The return type of the method.
     * @param calledInClass The class in which the method is called.
     * @param calledInMethod The method in which the method is called.
     * @param calledInMethodReturnType The return type of the calling method.
     * @param callMethod The method that invokes te call.
     * @param invokedBy The entity that invokes the call.
     * @return A new {@code Call} instance.
     */
    public static Call of(String classType, String methodName, String returnType, String calledInClass,
                          String calledInMethod, String calledInMethodReturnType, String callMethod, String invokedBy) {
        return new Call(classType, methodName, returnType, calledInClass, calledInMethod, calledInMethodReturnType, callMethod, invokedBy);
    }

    /**
     * Parses a method call from a string. The string should contain the method call details separated by semicolons.
     *
     * @param line The string representation of the method call.
     * @return A new {@code Call} instance created from the parsed string.
     */
    public static Call of(String line) {
        String[] fields = line.trim().split(";");
        return new Call(fields[0].trim(), fields[1].trim(), fields[2].trim(), fields[3].trim(), fields[4].trim(), fields[5].trim(), fields[6].trim().equals("null") ? null : fields[6].trim(), fields[7].trim());
    }

    /**
     * Checks if the {@code classType} of this method call is assignable to the given class.
     *
     * @param classe The name of the class to check against.
     * @return {@code true} if the method's class is assignable to the provided class, {@code false} otherwise.
     */
    public boolean isFrom(String classe) {
        String nomeDaClasse = classType.split("<")[0];
        AssignVerifier verifier = new AssignVerifier(classe);
        return verifier.isAssignable(nomeDaClasse);
    }

    /**
     * Returns a string representing the method call details in a structured format.
     *
     * @return A formatted string representing the method call.
     */
    public String callGraph() {
        return "<" + classType + "; " + methodName + "; " + returnType + "; " + calledInClass + "; " + calledInMethod + "; " + calledInMethodReturnType + "; " + callMethod + "; " + invokedBy + ">";
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getCalledInClass() {
        return calledInClass;
    }

    public void setCalledInClass(String calledInClass) {
        this.calledInClass = calledInClass;
    }

    public String getCalledInMethod() {
        return calledInMethod;
    }

    public void setCalledInMethod(String calledInMethod) {
        this.calledInMethod = calledInMethod;
    }

    public String getCalledInMethodReturnType() {
        return calledInMethodReturnType;
    }

    public void setCalledInMethodReturnType(String calledInMethodReturnType) {
        this.calledInMethodReturnType = calledInMethodReturnType;
    }

    public String getCallMethod() {
        return callMethod;
    }

    public void setCallMethod(String callMethod) {
        this.callMethod = callMethod;
    }

    public String getInvokedBy() {
        return invokedBy;
    }

    public void setInvokedBy(String invokedBy) {
        this.invokedBy = invokedBy;
    }

    /**
     * Generates a hash code for the {@code Call} object based on its attributes.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(classType, methodName, returnType, calledInClass, calledInMethod, calledInMethodReturnType, callMethod, invokedBy);
    }

    /**
     * Compares this {@code Call} object with another for equality.
     *
     * @param obj The object to compare with.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        Call other = (Call) obj;
        return Objects.equals(this.classType, other.classType) &&
            Objects.equals(this.methodName, other.methodName) &&
            Objects.equals(this.returnType, other.returnType) &&
            Objects.equals(this.calledInClass, other.calledInClass) &&
            Objects.equals(this.calledInMethod, other.calledInMethod) &&
            Objects.equals(this.callMethod, other.callMethod) &&
            Objects.equals(this.invokedBy, other.invokedBy);
    }

    @Override
    public String toString() {
        return "Call{" + "classType=" + classType + ", methodName=" + methodName + ", returnType=" + returnType +
            ", calledInClass=" + calledInClass + ", calledInMethod=" + calledInMethod +
            ", calledInMethodReturnType=" + calledInMethodReturnType + ", callMethod=" + callMethod +
            ", invokedBy=" + invokedBy + '}';
    }
}
