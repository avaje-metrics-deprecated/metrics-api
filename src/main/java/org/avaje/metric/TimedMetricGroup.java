package org.avaje.metric;

/**
 * A group of TimedMetric that share a common base name.
 * <p>
 * This is intended to be used when the full metric name is determined at runtime.
 */
public interface TimedMetricGroup {

  /**
   * Start the event for the given name.
   * <p>
   * The group and type parts of the metric name are common and the metrics only differ by this
   * name.
   * <p>
   * Typically the underlying implementation uses a cache to lookup the TimedMetric and create it if
   * necessary.
   * 
   * @param name
   *          the specific name for the metric (group and type name parts are common).
   * 
   * @return the TimedMetricEvent that has started.
   */
  public TimedEvent start(String name);

  /**
   * Return the TimedMetric for the specific name.
   */
  public TimedMetric getTimedMetric(String name);

}