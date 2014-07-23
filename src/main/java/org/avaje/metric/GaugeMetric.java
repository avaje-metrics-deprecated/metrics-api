package org.avaje.metric;

/**
 * Metric based on a gauge returning double values.
 */
public interface GaugeMetric extends Metric {

  /**
   * Return the value.
   */
  public double getValue();

}