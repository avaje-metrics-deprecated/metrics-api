package org.avaje.metric;

/**
 * The name of the metric.
 */
public interface MetricName extends Comparable<MetricName> {

  /**
   * Returns the group to which the metric belongs. For class-based metrics, this will be the
   * package name.
   */
  public String getGroup();

  /**
   * Returns the type to which the Metric belongs. For class-based metrics, this will be the simple
   * class name.
   */
  public String getType();

  /**
   * Returns the name of the metric.
   */
  public String getName();

  /**
   * Return a simple java like name.
   */
  public String getSimpleName();

}