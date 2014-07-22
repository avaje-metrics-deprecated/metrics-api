package org.avaje.metric;

/**
 * Gauge that reports long values.
 */
public interface GaugeCounterMetric extends Metric {

  /**
   * Return the value.
   */
  public long getValue();

}