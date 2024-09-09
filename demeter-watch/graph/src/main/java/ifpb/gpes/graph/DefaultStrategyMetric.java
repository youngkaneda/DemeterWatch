package ifpb.gpes.graph;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * DefaultStrategyMetric is an implementation of the {@code StrategyMetric} interface.
 * <p>
 * This class calculates a metric by dividing the number of incoming calls (callIn)
 * by the number of outgoing calls (callOut) using {@code BigDecimal} for precision.
 * The computation uses the {@code MathContext.DECIMAL32} for rounding and precision management.
 * </p>
 */
public class DefaultStrategyMetric implements StrategyMetric {

    /**
     * Computes the metric as the ratio of {@code callIn} to {@code callOut}.
     *
     * @param callIn the number of incoming method calls.
     * @param callOut the number of outgoing method calls.
     * @return a {@code BigDecimal} representing the computed ratio.
     * @throws ArithmeticException if {@code callOut} is zero.
     */
    @Override
    public BigDecimal compute(int callIn, int callOut) {
        return new BigDecimal(callIn)
            .divide(new BigDecimal(callOut), MathContext.DECIMAL32);
    }
}
