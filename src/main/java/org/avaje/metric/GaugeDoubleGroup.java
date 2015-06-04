package org.avaje.metric;

/**
 * Groups {@link GaugeDoubleMetric} that are closely related.
 * <p>
 * Used for example to group the JVM Memory gauges together such as Percentage Used, Max Memory,
 * Initial Memory, Committed Memory.
 */
public interface GaugeDoubleGroup extends Metric {

  /**
   * Return the metrics in this group.
   */
  GaugeDoubleMetric[] getGaugeMetrics();

}