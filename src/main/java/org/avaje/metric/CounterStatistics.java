package org.avaje.metric;

/**
 * Statistics provided by the {@link CounterMetric}.
 */
public interface CounterStatistics {

  /**
   * Return the time the counter started statistics collection.
   */
  public long getStartTime();

  /**
   * Return the count of values collected.
   */
  public long getCount();
}