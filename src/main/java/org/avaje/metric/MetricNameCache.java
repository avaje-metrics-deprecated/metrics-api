package org.avaje.metric;

/**
 * Cache of MetricNames that share a common base name.
 */
public interface MetricNameCache {

  /**
   * Return the MetricName from the cache creating it if required.
   * <p>
   * Typically the name passed in could be a soap operation name or method name.
   * </p>
   */
  public MetricName get(String name);

}