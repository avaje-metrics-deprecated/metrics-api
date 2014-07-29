package org.avaje.metric;

/**
 * A Gauge that returns a single value (long).
 * <p>
 * A Gauge typically doesn't represent an "Event" but the current value of a resource like threads,
 * memory etc.
 * <p>
 * A {@link GaugeCounterMetric} is created by registering a GaugeCounter via
 * {@link MetricManager#register(MetricName, GaugeCounter)}
 */
public interface GaugeCounter {

  /**
   * Return the current value.
   */
  public long getValue();

}
