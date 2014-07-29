package org.avaje.metric;

/**
 * Collects and provides statics for a timed event.
 * <p>
 * Typically collects execution statistics for a method.
 * <p>
 * Note that success and error statistics are kept separately and reported separately. It is
 * generally useful to know which methods are invoking errors and in addition the timing of success
 * and error execution can be very different.
 * <p>
 * A TimedMetric is returned via {@link MetricManager#getTimedMetric(MetricName)} (or variant). This
 * will automatically create and register the TimedMetric if it does not already exist.
 */
public interface TimedMetric extends Metric {

  /**
   * Return the success statistics.
   */
  public ValueStatistics getCollectedSuccessStatistics();

  /**
   * Return the error statistics.
   */
  public ValueStatistics getCollectedErrorStatistics();

  /**
   * Return the current success statistics choosing is the underlying statistics should be reset.
   */
  public ValueStatistics getSuccessStatistics(boolean reset);

  /**
   * Return the error success statistics choosing is the underlying statistics should be reset.
   */
  public ValueStatistics getErrorStatistics(boolean reset);

  /**
   * Start an event.
   * <p>
   * At the completion of the event {@link TimedEvent#endWithSuccess()},
   * {@link TimedEvent#endWithError()} or {@link TimedEvent#end(boolean)} are called to record the
   * event duration and success or otherwise.
   * </p>
   */
  public TimedEvent startEvent();

  /**
   * Add an event based on a startNanos (determined by {@link System#nanoTime()}).
   * <p>
   * Success and failure statistics are kept separately.
   * <p>
   * This is an alternative to using {@link #startEvent()}.
   */
  public void addEventSince(boolean success, long startNanos);

  /**
   * Add an event duration in nanoseconds noting if it was a success or failure result.
   * <p>
   * Success and failure statistics are kept separately.
   * <p>
   * This is an alternative to using {@link #startEvent()}.
   */
  public void addEventDuration(boolean success, long durationNanos);

  /**
   * Add an event duration with opCode indicating success or failure. This is intended for use by
   * enhanced code and not general use.
   */
  public void operationEnd(long durationNanos, int opCode);

}