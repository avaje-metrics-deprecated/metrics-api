package org.avaje.metric;

/**
 * A group of GaugeMetrics.
 */
public interface GaugeMetricGroup extends Metric {

  /**
   * Return the GaugeMetric's in this group.
   */
  public GaugeMetric[] getGaugeMetrics();

}