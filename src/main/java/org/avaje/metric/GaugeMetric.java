package org.avaje.metric;

/**
 * A Gauge returning double values.
 */
public interface GaugeMetric extends Metric {

  /**
   * Return the value.
   */
  public double getValue();

}