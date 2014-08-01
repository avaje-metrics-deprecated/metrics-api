package org.avaje.metric;

/**
 * A Metric collects statistics on events.
 * <p>
 * {@link TimedMetric}, {@link CounterMetric}, {@link ValueMetric}, {@link GaugeDoubleMetric} and
 * {@link GaugeLongMetric} are the common more specific metric types.
 */
public interface Metric {

  /**
   * Return the name of the metric.
   */
  public MetricName getName();

  /**
   * Only called by the MetricManager this tells the metric to collect its underlying statistics for
   * reporting purposes reseting internal counters.
   * 
   * @return true if this metric has some values. Returning false means that no events occurred
   *         since the last collection and typically a reporter omits this metric from the output
   *         that is sent.
   */
  public boolean collectStatistics();

  /**
   * Visit the metric typically reading and reporting the underlying statistics.
   */
  public void visit(MetricVisitor visitor);

  /**
   * Clear the statistics reseting any internal counters etc.
   */
  public void clearStatistics();

}
