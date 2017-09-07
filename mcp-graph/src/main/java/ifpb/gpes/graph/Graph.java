package ifpb.gpes.graph;

import br.edu.ifpb.gpes.mcp.core.Call;

/**
 *
 * @author juan
 */
public interface Graph {
    Matrix generateMatrix();
    void adicionarNos(Call call);
}
