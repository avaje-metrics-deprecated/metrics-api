package org.avaje.metric;

/**
 * A timing point entry when collecting nested context timing.
 */
public interface RequestTimingEntry {

  /**
   * Return the metric this entry is from.
   */
  TimedMetric getMetric();

  /**
   * Returns the depth of the entry.
   */
  int getDepth();

  /**
   * Returns the start nanos value for the entry.
   */
  long getStartNanos();

  /**
   * Returns the end nanos value for the entry.
   */
  long getEndNanos();
}
