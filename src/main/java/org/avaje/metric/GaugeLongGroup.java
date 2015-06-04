package org.avaje.metric;

/**
 * Groups {@link GaugeLongMetric} gauges that are closely related.
 * <p>
 * For example this used to group the JVM Thread gauges such as 'active threads' and 'total threads'
 * together.
 */
public interface GaugeLongGroup extends Metric {

  /**
   * Return the metrics in this group.
   */
  GaugeLongMetric[] getGaugeMetrics();

}