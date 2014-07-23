package org.avaje.metric;

/**
 * Metric based on a counter (long value).
 * <p>
 * Can be used to count units like bytes, rows, events but generally not time.
 */
public interface CounterMetric extends Metric {

  /**
   * Return the current statistics.
   */
  public CounterStatistics getStatistics(boolean reset);

  /**
   * Return the collected statistics. This is used by reporting objects.
   */
  public CounterStatistics getCollectedStatistics();

  /**
   * Mark that 1 event has occurred.
   */
  public void markEvent();

  /**
   * Mark that numberOfEventsOccurred events have occurred.
   */
  public void markEvents(long numberOfEventsOccurred);

}