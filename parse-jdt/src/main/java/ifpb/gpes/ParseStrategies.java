package ifpb.gpes;

import ifpb.gpes.jdt.SmartParseJDT;
import java.util.List;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 06/07/2017, 19:07:59
 */
public enum ParseStrategies implements ParseStrategy {
    JDT {
        @Override
        public List<No> from(Project project) {
            return new SmartParseJDT().from(project);
        }

    }
}
