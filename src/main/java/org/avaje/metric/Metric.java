package org.avaje.metric;

import java.io.IOException;

/**
 * A Metric that collects statistics on events.
 * <ul>
 *   <li>TimedMetric and BucketTimedMetric are used for monitoring execution time</li>
 *   <li>CounterMetric is for counting discrete events like 'user logged in'</li>
 *   <li>ValueMetric is used when events have a value like bytes sent, lines read</li>
 *   <li>Gauges measure the current value of a resource like 'used memory' or 'active threads'.</li>
 * </ul>
 */
public interface Metric {

  /**
   * Return the name of the metric.
   */
  MetricName getName();

  /**
   * Typically this is only called by the MetricManager and tells the metric to collect its underlying statistics for
   * reporting purposes and in addition resetting and internal counters it has.
   *
   * @return true if this metric has some values. Returning false means that no events occurred
   *         since the last collection and typically a reporter omits this metric from the output
   *         that is sent.
   */
  boolean collectStatistics();

  /**
   * Visit the metric typically reading and reporting the underlying statistics.
   * <p>
   * Typically this is use by reporters to traverse all the collected metrics for sending to a file or repository.
   * </p>
   */
  void visit(MetricVisitor visitor) throws IOException;

  /**
   * Clear the statistics resetting any internal counters etc.
   * <p>
   * Typically the MetricManager takes care of resetting the statistic/counters for the metrics when
   * it periodically collects and reports all the metrics and you are not expected to use this method.
   * </p>
   */
  void clearStatistics();

}
