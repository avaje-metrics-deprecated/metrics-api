package org.avaje.metric;

/**
 * A Gauge returns a single value (double).
 * <p>
 * A Gauge typically doesn't represent an "Event" but the current value of a
 * resource like threads, memory etc.
 * <p>
 * A {@link GaugeMetric} is created by registering a Gauge via
 * {@link MetricManager#register(MetricName, Gauge)}
 */
public interface Gauge {

  /**
   * Return the current value.
   */
  public double getValue();

}
