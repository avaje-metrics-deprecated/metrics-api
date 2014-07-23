package org.avaje.metric;

/**
 * A group of GaugeCounterMetrics that can be related.
 * <p>
 * Used to group gauges together for example JVM GC gauges.
 */
public interface GaugeCounterMetricGroup extends Metric {

  /**
   * Return the group of metrics.
   */
  public GaugeCounterMetric[] getGaugeMetrics();

}