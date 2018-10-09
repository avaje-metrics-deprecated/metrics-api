package org.avaje.metric;

import java.util.Map;

/**
 * A TimedMetric for measuring execution time for methods and events.
 */
public interface TimedMetric extends Metric {

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
   * Return true if this timed metric is part of a bucket range (and hence only hold statistics for the
   * bucket range returned by <code>bucketRange()</code>.
   */
  boolean isBucket();

  /**
   * Return the bucket range or empty string if not a bucket.
   */
  String getBucketRange();

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
   * Add an event duration with opCode indicating success or failure. This is intended for use by
   * enhanced code and not general use.
   */
  void operationEnd(int opCode, long startNanos);

  /**
   * Return true if this TimedMetric has been pushed onto an active context for this thread.
   * <p>
   * This means that the current thread is actively collecting timing entries and this metric
   * has been pushed onto the nested context.
   * </p>
   */
  boolean isActiveThreadContext();

  /**
   * Specify to collect per request detailed timing collection. The collectionCount is the number
   * of requests to collect detailed timing for and then automatically turn off.
   * <p>
   * This is expected to only be explicitly called on 'top level' metrics such as web endpoints.
   * Once a request timing context has been created by the top level metric then 'nested metrics'
   * (typically service and data access layers) can append to that existing context.  In this way
   * detailed per request level timing entries can be collected for only selected endpoints.
   * </p>
   */
  void setRequestTimingCollection(int collectionCount);

  /**
   * Return the number of remaining requests to collect detailed timing on.
   * <p>
   * This value starts out as the value set by #setRequestTimingCollection and then decrements
   * after a request timing is reported until it reaches 0 at which point request timing automatically
   * turns off for this metric.
   * </p>
   */
  int getRequestTimingCollection();

  /**
   * Decrement the request timing collection count.
   * <p>
   * This is typically called internally when a request timing is reported and generally not
   * expected to be called by application code.
   */
  void decrementCollectionCount();

  /**
   * Return extra attributes that can be included in the request logging.
   */
  Map<String, String> attributes();
}
