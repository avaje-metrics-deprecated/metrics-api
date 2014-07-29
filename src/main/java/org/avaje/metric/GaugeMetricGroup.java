package org.avaje.metric;

/**
 * A group of GaugeMetrics.
 * <p>
 * Used to group gauges together for example JVM GC gauges.
 */
public interface GaugeMetricGroup extends Metric {

  /**
   * Return the GaugeMetric's in this group.
   */
  public GaugeMetric[] getGaugeMetrics();

}