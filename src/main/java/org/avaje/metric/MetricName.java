package org.avaje.metric;

/**
 * The name of the metric.
 */
public interface MetricName extends Comparable<MetricName> {

  /**
   * Returns the group to which the metric belongs. For class-based metrics, this will be the
   * package name.
   */
  String getGroup();

  /**
   * Returns the type to which the Metric belongs. For class-based metrics, this will be the simple
   * class name.
   */
  String getType();

  /**
   * Returns the name of the metric.
   */
  String getName();

  /**
   * Return a simple java like name.
   */
  String getSimpleName();

  /**
   * Create and return another MetricName based on this instance with an additional suffix added to
   * the name.
   * 
   * @param nameSuffix
   *          The addition text added to this metrics name.
   */
  MetricName withSuffix(String nameSuffix);
}