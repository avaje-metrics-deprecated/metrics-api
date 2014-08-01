package org.avaje.metric;

/**
 * Metric that collects long values (e.g. total bytes sent).
 * <p>
 * Used when events have a value such as bytes sent, bytes received, rows read etc.
 * 
 * <pre>
 * <code>
 *  // Declare the metric (typically as a static field)
 *  static final ValueMetric totalBytesSentMetric = MetricManager.getValueMetric(MyService.class, "totalBytesSent");
 *  ...
 *  
 *  public void performSomeIO() {
 *  
 *    long bytesSent = ...
 *    
 *    totalBytesSentMetric.addEvent(bytesSent);
 *    ...
 *  }
 *  
 * </code>
 * </pre> 
 */
public interface ValueMetric extends Metric {

  /**
   * Return the statistics collected.
   */
  public ValueStatistics getCollectedStatistics();

  /**
   * Add a value (bytes, time, rows etc).
   */
  public void addEvent(long value);

}