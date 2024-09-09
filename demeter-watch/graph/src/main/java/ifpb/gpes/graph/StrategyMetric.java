package ifpb.gpes.graph;

import java.math.BigDecimal;

/**
 * The {@code StrategyMetric} interface defines the method for computing a metric value
 * based on the number of incoming and outgoing calls.
 * <p>
 * Implementations of this interface provide custom strategies for computing metrics
 * in graph or relationship-based models.
 * </p>
 */
public interface StrategyMetric {

    /**
     * Computes the metric value based on the number of incoming and outgoing calls.
     *
     * @param callIn  the number of incoming calls
     * @param callOut the number of outgoing calls
     * @return the computed metric value as a {@code BigDecimal}
     */
    public BigDecimal compute(int callIn, int callOut);
}
