package org.avaje.metric.statistics;

import org.avaje.metric.GaugeLongMetric;

/**
 * Statistics provided by the {@link GaugeLongMetric}.
 *
 */
public interface GaugeLongStatistics extends MetricStatistics {

  /**
   * Return the time the counter started statistics collection.
   */
  long getStartTime();

  /**
   * Return the count of values collected.
   */
  long getValue();
}
