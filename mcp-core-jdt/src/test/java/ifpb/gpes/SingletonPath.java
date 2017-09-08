package ifpb.gpes;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 06/07/2017, 18:25:58
 */
public interface SingletonPath {

    public static final String ROOT = resolveRootPath();

    // TODO resolver como o projeto irá pegar o caminho do projeto 
    public static String resolveRootPath() {
        return "";
    }
    
}
