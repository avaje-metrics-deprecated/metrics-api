package org.avaje.metric;

/**
 * Statistics collected by ValueMetric or TimedMetric.
 */
public interface ValueStatistics {

  /**
   * Return the time these statistics were collected from.
   * <p>
   * This should equate to the last time the statistics were collected for reporting purposes so if
   * that is ever minute then this would return the epoch time of 1 minute ago.
   */
  public long getStartTime();

  /**
   * Return the count of values collected (since the last reset/collection).
   */
  public long getCount();

  /**
   * Return the total of all the values (since the last reset/collection).
   */
  public long getTotal();

  /**
   * Return the Max value collected (since the last reset/collection).
   */
  public long getMax();

  /**
   * Return the mean value rounded up for the values collected since the last reset/collection.
   */
  public long getMean();

}