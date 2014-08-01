package org.avaje.metric;

/**
 * Groups {@link GaugeDoubleMetric} that are closely related.
 * <p>
 * Used to group gauges together for example JVM GC gauges, JVM Thread gauges etc.
 */
public interface GaugeDoubleGroup extends Metric {

  /**
   * Return the GaugeMetric's in this group.
   */
  public GaugeDoubleMetric[] getGaugeMetrics();

}