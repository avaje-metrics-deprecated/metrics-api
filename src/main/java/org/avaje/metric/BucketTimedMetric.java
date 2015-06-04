package org.avaje.metric;

/**
 * Used to collect timed metrics but puts the statistics into millisecond time ranges (buckets).
 * <p>
 * In providing millisecond bucket ranges of 200, 400, 600 this will create 4 buckets to put the
 * execution statistics into. The first range will be for all events that take between 0 and 200
 * milliseconds, the second range will be for all events that take between 200 and 400 milliseconds,
 * the third range will take events that take between 400 and 600 milliseconds and the last bucket
 * takes all events that take longer than 600 milliseconds.
 * <p>
 * The general purpose of using a BucketTimedMetric over a TimedMetric is that it can provide an
 * insight into how the execution times are statistically distributed. For example, a method
 * execution might have 2 paths with one hitting a cache and generally a lot faster. In using 2 or
 * more buckets you might get a more representative view of the 2 distinct execution paths and
 * monitor the slow non-cached execution path more accurately.
 * <p>
 * <em>Example:</em>
 * 
 * <pre>
 * <code>
 *  
 *  // Declare the metric with 4 bucket ranges of:
 *  //    0 - 100 milliseconds
 *  //  100 - 200 milliseconds
 *  //  200 - 300 milliseconds
 *  //  300+      milliseconds
 *  
 *  static final BucketTimedMetric timedUserLogin = MetricManager.getBucketTimedMetric(MyService.class, "performLogin", 100, 200, 300);
 *  ...
 *  
 *  public void performLogin() {
 *    
 *    long startNanos = System.nanoTime();
 *    
 *    try {
 *      ...
 *    
 *    
 *    } finally {
 *      // Add the event to the success statistics
 *      timedUserLogin.addEventSince(true, startNanos);
 *    }
 *  }
 *  
 * </code>
 * </pre>
 * <p>
 * Instead of programmatically adding BucketTimedMetric code these can be added using enhancement.
 * Classes that are annotated with <code>@Timed</code> on the class or method can have the
 * appropriate code added via enhancement. Also note that the enhancement in addition can be applied
 * to classes annotated with <code>@Singleton</code>, JAX-RS annotations like <code>@Path</code> and
 * Spring stereotype annotations like <code>@Component</code>, <code>@Service</code> etc.
 * <p>
 * The enhancement will add instructions such that for method execution that throw exceptions the
 * timing is put into the error statistics. This can provide quick insight into where errors are
 * occurring, how frequently and execution time of errors.
 * 
 * <p>
 * <em>Example:</em> <code>@Timed</code> annotation.
 * 
 * <pre>
 * <code>
 *  ...
 *  {@literal @}Timed(buckets=100,200,300)
 *  public void performLogin() {
 *    ...
 *  }
 *  
 * </code>
 * </pre>
 */
public interface BucketTimedMetric extends Metric {

  /**
   * Return ranges times in milliseconds.
   * <p>
   * These values are more accurately described as the top value in milliseconds of end range except
   * the last bucket which is unbounded. For example, with bucket ranges of 100,200,300 there are 4
   * ranges - 0 to 100 millis, 100 to 200 millis, 200 to 300 millis and 300+.
   */
  int[] getBucketRanges();

  /**
   * Return the underlying TimedMetrics with one per bucket.
   */
  TimedMetric[] getBuckets();

  /**
   * Start an event.
   * <p>
   * At the completion of the event {@link TimedEvent#endWithSuccess()},
   * {@link TimedEvent#endWithError()} or {@link TimedEvent#end(boolean)} are called to record the
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
  void operationEnd(int opCode, long startNanos);
}
