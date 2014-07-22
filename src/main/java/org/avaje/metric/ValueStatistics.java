package org.avaje.metric;

/**
 * Statistics collected by ValueMetric or TimedMetric.
 */
public interface ValueStatistics {

  /**
   * Return the time the counter started statistics collection.
   */
  public long getStartTime();

  /**
   * Return the count of values collected.
   */
  public long getCount();

  /**
   * Return the total of all the values.
   */
  public long getTotal();

  /**
   * Return the Max value collected.
   */
  public long getMax();

  /**
   * Return the mean value rounded up.
   */
  public long getMean();

}