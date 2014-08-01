package org.avaje.metric;

/**
 * Metric based on a counter (long value).
 * <p>
 * Can be used to count units like bytes, rows, events but generally not time.
 * <pre>
 * <code>
 *  // Declare the counter (typically as a static field)
 *  static final CounterMetric userLoginCounter = MetricManager.getCounterMetric(MyService.class, "userLogin");
 *  ...
 *  
 *  public void performUserLogin() {
 *  
 *    // increment the counter
 *    userLoginCounter.markEvent();
 *    ...
 *  }
 *  
 * </code>
 * </pre>
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