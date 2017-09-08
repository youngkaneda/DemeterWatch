package ifpb.gpes.graph;

import ifpb.gpes.Call;

/**
 *
 * @author juan
 */
public interface Graph {
    Matrix generateMatrix();
    void adicionarNos(Call call);
    //TODO: metodo para criar o graph
}
