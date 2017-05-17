package ifpb.gpes.jdt;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juan
 */
class No {
    private String nome;
    private String returnType;
    private List<Method> invocacoes;
    
    public No(String nome, String returnType){
        this.nome = nome;
        this.returnType = returnType;
        this.invocacoes = new ArrayList<>();
    }
    
    public boolean addMethod(Method method){
        return this.invocacoes.add(method);
    }

    @Override
    public String toString() {
        return "Node{" + "nome=" + nome + ", returnType=" + returnType + ", invocacoes=" + invocacoes + '}';
    }
}