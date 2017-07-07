
import ifpb.gpes.Project;
import ifpb.gpes.Study;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 07/07/2017, 15:24:30
 */
public class ClienteControleFilmes {

    public static void main(String[] args) {
        Project project = Project
                .root("/Users/job/Documents/dev/gpes/parse-review/controle-filmes/")
                .path("src/")
                .sources("src/")
                .filter(".java");

        Study.of(project)
                .toFile("controle-filmes")
                .execute();
    }
}
