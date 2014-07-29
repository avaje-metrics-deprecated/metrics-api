package org.avaje.metric;

/**
 * Metric based on a gauge returning double values.
 * <p>
 * A {@link GaugeMetric} is created by registering a Gauge via
 * {@link MetricManager#register(MetricName, Gauge)}
 */
public interface GaugeMetric extends Metric {

  /**
   * Return the value.
   */
  public double getValue();

}