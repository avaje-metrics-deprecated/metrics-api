package org.avaje.metric.statistics;

import org.avaje.metric.GaugeDoubleMetric;

/**
 * Statistics provided by the {@link GaugeDoubleMetric}.
 *
 */
public interface GaugeDoubleStatistics extends MetricStatistics {

  /**
   * Return the time the counter started statistics collection.
   */
  long getStartTime();

  /**
   * Return the count of values collected.
   */
  double getValue();
}
