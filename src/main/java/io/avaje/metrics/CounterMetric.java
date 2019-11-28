package io.avaje.metrics;

/**
 * Metric based on a counter (long value) typically used to count discrete events.
 * <p>
 * Can be used to count discrete events like 'user login'. {@link ValueMetric} would typically
 * be used when the event has a value (bytes sent, bytes received, lines read etc).
 * <pre>
 * <code>
 *  // Declare the counter (typically as a static field)
 *  static final CounterMetric userLoginCounter = MetricManager.counter(MyService.class, "userLogin");
 *  ...
 *
 *  void performUserLogin() {
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
   * Mark that 1 event has occurred.
   */
  void markEvent();

  /**
   * Mark that numberOfEventsOccurred events have occurred.
   */
  void markEvents(long numberOfEventsOccurred);

  /**
   * Return the current count.
   */
  long getCount();

}
