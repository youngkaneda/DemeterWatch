package ifpb.gpes.graph;

import java.math.BigDecimal;

/**
 * The {@code Metric} class represents a metric between two entities (source and target),
 * tracking the number of incoming and outgoing calls, and computing a value using a
 * specified strategy.
 * <p>
 * This class can be used to model relationships between entities in a graph structure
 * and compute metrics based on customizable strategies provided by {@code StrategyMetric}.
 * </p>
 */
public class Metric {

    private final String source;

    private final String target;

    private final int callIn;

    private final int callOut;

    private final StrategyMetric strategyMetric;

    /**
     * Constructs a {@code Metric} with the specified source, target, incoming and outgoing
     * calls, and a custom strategy.
     *
     * @param source the source entity
     * @param target the target entity
     * @param callIn the number of incoming calls
     * @param callOut the number of outgoing calls
     * @param strategy the strategy used to compute the metric
     */
    public Metric(String source, String target, int callIn, int callOut, StrategyMetric strategy) {
        this.source = source;
        this.target = target;
        this.callIn = callIn;
        this.callOut = callOut;
        this.strategyMetric = strategy;
    }

    /**
     * Constructs a {@code Metric} with the specified source, target, incoming and outgoing
     * calls, using the default strategy {@code DefaultStrategyMetric}.
     *
     * @param source the source entity
     * @param target the target entity
     * @param callIn the number of incoming calls
     * @param callOut the number of outgoing calls
     */
    public Metric(String source, String target, int callIn, int callOut) {
        this(source, target, callIn, callOut, new DefaultStrategyMetric());
    }

    /**
     * Computes the metric value using the strategy associated with this metric.
     *
     * @return the computed metric value as a {@code BigDecimal}
     */
    public BigDecimal value() {
        return this.strategyMetric.compute(callIn, callOut);
    }

    /**
     * Returns a string representation of the metric in the format "source -> target in:X out:Y metric:Z".
     *
     * @return a string representation of the metric
     */
    @Override
    public String toString() {
        return new StringBuilder(source)
            .append(" -> ").append(target)
            .append(" in").append(":").append(callIn)
            .append(" out").append(":").append(callOut)
            .append(" metric").append(":").append(value())
            .toString();
    }
}
