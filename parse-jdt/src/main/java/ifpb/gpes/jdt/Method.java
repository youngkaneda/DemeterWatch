package ifpb.gpes.jdt;

/**
 *
 * @author Juan
 */
class Method {
    private String nome;
    private String returnype;

    public Method(String nome, String returnype) {
        this.nome = nome;
        this.returnype = returnype;
    }

    @Override
    public String toString() {
        return "Method{" + "nome=" + nome + ", returnype=" + returnype + '}';
    }
 
}

