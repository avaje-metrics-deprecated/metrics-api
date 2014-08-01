package org.avaje.metric;

/**
 * Groups {@link GaugeLongMetric} gauges that are closely related.
 * <p>
 * Used to group gauges together for example JVM GC gauges, JVM Thread gauges etc.
 */
public interface GaugeLongGroup extends Metric {

  /**
   * Return the group of metrics.
   */
  public GaugeLongMetric[] getGaugeMetrics();

}