package org.avaje.metric;

/**
 * Metric based on an underlying gauge that reports long values.
 * <p>
 * A {@link GaugeCounterMetric} is created by registering a GaugeCounter via
 * {@link MetricManager#register(MetricName, GaugeCounter)}
 */
public interface GaugeCounterMetric extends Metric {

  /**
   * Return the value.
   */
  public long getValue();

}