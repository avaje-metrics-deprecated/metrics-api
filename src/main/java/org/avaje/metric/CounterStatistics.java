package org.avaje.metric;

public interface CounterStatistics {

  /**
   * Return the time the counter started statistics collection.
   */
  public long getStartTime();

  /**
   * Return the count of values collected.
   */
  public long getCount();
}