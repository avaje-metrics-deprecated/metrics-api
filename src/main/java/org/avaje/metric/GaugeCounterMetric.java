package org.avaje.metric;

/**
 * Metric based on an underlying gauge that reports long values.
 */
public interface GaugeCounterMetric extends Metric {

  /**
   * Return the value.
   */
  public long getValue();

}