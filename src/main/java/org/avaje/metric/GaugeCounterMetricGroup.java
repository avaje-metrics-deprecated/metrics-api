package org.avaje.metric;

/**
 * A group of GaugeCounterMetrics.
 */
public interface GaugeCounterMetricGroup extends Metric {

  /**
   * Return the group of metrics.
   */
  public GaugeCounterMetric[] getGaugeMetrics();

}