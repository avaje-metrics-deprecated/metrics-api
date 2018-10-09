package org.avaje.metric.statistics;

import org.avaje.metric.CounterMetric;

/**
 * Statistics provided by the {@link CounterMetric}.
 */
public interface CounterStatistics extends MetricStatistics {

  /**
   * Return the time the counter started statistics collection.
   */
  long getStartTime();

  /**
   * Return the count of values collected.
   */
  long getCount();
}
