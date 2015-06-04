package org.avaje.metric;

/**
 * A TimedEvent that is ended with either success or error.
 * <p>
 * Note that it is generally preferred to use {@link TimedMetric#addEventSince(boolean, long)} as
 * that avoids an object creation and the associated GC so has slightly less overhead.
 * <p>
 * Example:
 * 
 * <pre>
 * <code>
 *  TimedMetric metric = MetricManager.getTimedMetric(MyService.class, "sayHello");
 *  ...
 *  
 *  TimedEvent timedEvent = metric.startEvent();
 *  try {
 *    ...
 *  
 *  } finally {
 *    // Add the event to the 'success' statistics
 *    timedEvent.endWithSuccess();
 *  }
 *  
 * </code>
 * </pre>
 * 
 * @see TimedMetric#startEvent()
 */
public interface TimedEvent {

  /**
   * End specifying whether the event was successful or in error.
   */
  void end(boolean withSuccess);

  /**
   * This timed event ended with successful execution (e.g. Successful SOAP Operation or SQL
   * execution).
   */
  void endWithSuccess();

  /**
   * This timed event ended with an error or fault execution (e.g. SOAP Fault or SQL exception).
   */
  void endWithError();

}