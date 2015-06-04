package org.avaje.metric;

/**
 * Common behavior for both TimedMetric and BucketTimedMetric.
 */
public interface AbstractTimedMetric extends Metric {

  /**
   * Start an event.
   * <p>
   * At the completion of the event one of {@link TimedEvent#endWithSuccess()},
   * {@link TimedEvent#endWithError()} or {@link TimedEvent#end(boolean)} is called to record the
   * event duration and success or otherwise.
   * <p>
   * This is an alternative to using {@link #addEventSince(boolean, long)} or
   * {@link #addEventDuration(boolean, long)}. Note that this startEvent() method has slightly
   * higher overhead as it instantiates a TimedEvent object which must be later GC'ed. In this sense
   * generally addEventSince() is the preferred method to use.
   */
  TimedEvent startEvent();

  /**
   * Add an event based on a startNanos (determined by {@link System#nanoTime()}).
   * <p>
   * Success and failure statistics are kept separately.
   * <p>
   * This is an alternative to using {@link #startEvent()}. Note that using startEvent() has
   * slightly higher overhead as it instantiates a TimedEvent object which must be later GC'ed. In
   * this sense generally addEventSince() is the preferred method to use.
   */
  void addEventSince(boolean success, long startNanos);

  /**
   * Add an event duration in nanoseconds noting if it was a success or failure result.
   * <p>
   * Success and failure statistics are kept separately.
   * <p>
   * This is an alternative to using {@link #addEventSince(boolean, long)} where you pass in the
   * duration rather than the start nanoseconds.
   */
  void addEventDuration(boolean success, long durationNanos);

  /**
   * Add an event duration with opCode indicating success or failure. This is intended for use by
   * enhanced code and not general use.
   * <p>
   * Although this looks redundant and a little ugly having this method means that for enhancement
   * the added byte code is minimised. In the case where metric collection is turned off overhead is
   * limited to a System.nanoTime() call and a noop method call.
   */
  void operationEnd(int opCode, long startNanos, boolean activeThreadContext);

  /**
   * Return true if this TimedMetric has been pushed onto an active context for this thread.
   * <p>
   * This means that the current thread is actively collecting timing entries and this metric
   * has been pushed onto the nested context.
   * </p>
   */
  boolean isActiveThreadContext();

  /**
   * Turn on or off pre request detailed timing collection.
   * <p>
   * This is expected to only be explicitly called on 'top level' metrics such as web endpoints.
   * Once a request timing context has been created by the top level metric then 'nested metrics'
   * (typically service and data access layers) can append to that existing context.  In this way
   * detailed per request level timing entries can be collected for only selected endpoints.
   * </p>
   */
  void setRequestTiming(boolean requestTiming);

  /**
   * Return true if this metric has explicitly got pre request detailed timing collection turned on.
   * <p>
   * This is expected to be set to true only on selected 'top level' metrics such as web endpoints.
   * </p>
   */
  boolean isRequestTiming();

}