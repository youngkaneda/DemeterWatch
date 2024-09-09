package ifpb.gpes.graph;

import java.util.Objects;

/**
 * The {@code Node} class represents a node in a graph structure, capturing some details about
 * the {@link ifpb.gpes.Call} object, like the class name of the method call, its name, return type,
 * and who invoked it.
 */
public class Node {

    private String className;
    private String methodName;
    private String returnType;
    private String invokedBy;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public String getInvokedBy() {
        return invokedBy;
    }

    public void setInvokedBy(String invokedBy) {
        this.invokedBy = invokedBy;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.className);
        hash = 59 * hash + Objects.hashCode(this.methodName);
        hash = 59 * hash + Objects.hashCode(this.returnType);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if (!Objects.equals(this.className, other.className)) {
            return false;
        }
        if (!Objects.equals(this.methodName, other.methodName)) {
            return false;
        }
        if (!Objects.equals(this.returnType, other.returnType)) {
            return false;
        }
        if (!Objects.equals(this.invokedBy, other.invokedBy)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Node{" +
            "className='" + className + '\'' +
            ", methodName='" + methodName + '\'' +
            ", returnType='" + returnType + '\'' +
            ", invokedBy='" + invokedBy + '\'' +
            '}';
    }
}
