package org.avaje.metric.statistics;

import org.avaje.metric.MetricName;

/**
 * Common for statistics of all metrics.
 */
public interface MetricStatistics {

  /**
   * Return the assoicated metric name.
   */
  MetricName getName();

  /**
   * Visit the reporter for the given metric type.
   */
  void visit(MetricStatisticsVisitor reporter);

}
