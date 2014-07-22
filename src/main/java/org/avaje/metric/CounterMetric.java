package org.avaje.metric;


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