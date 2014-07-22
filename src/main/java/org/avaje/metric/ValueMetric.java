package org.avaje.metric;

/**
 * Metric that collects long values.
 * <p>
 * Typically used to collect bytes, rows, time etc.
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