package org.avaje.metric.statistics;

/**
 * Statistics collected by TimedMetric.
 */
public interface TimedStatistics extends ValueStatistics {

  /**
   * Return true if this is bucket range based.
   */
  boolean isBucket();

  /**
   * Return the bucket range for these statistics.
   */
  String getBucketRange();

}
