package org.avaje.metric.spi;

import java.util.Collection;
import java.util.List;

import org.avaje.metric.*;

/**
 * The SPI for the underlying implementation that is plugged in via service locator.
 */
public interface PluginMetricManager {

  /**
   * Create a MetricName based on the class and name.
   * Typically name is a method name.
   */
  MetricName name(Class<?> cls, String name);

  /**
   * Create a Metric name based on group, type and name.
   * 
   * @param group
   *          The group which often maps to a package name.
   * @param type
   *          The type which often maps to a simple class name.
   * @param name
   *          The name which often maps to a method name.
   */
  MetricName name(String group, String type, String name);

  /**
   * Create a Metric name by parsing a name that is expected to include periods.
   * <p>
   * The name is expected to be in dot notation similar to <code>package.class.method</code>.
   */
  MetricName name(String name);
  
  /**
   * Return the TimedMetric using the metric name.
   */
  TimedMetric getTimedMetric(MetricName name);

  /**
   * Return the BucketTimedMetric using the given base metric name and bucketRanges.
   * 
   * @param name
   *          The metric name
   * @param bucketRanges
   *          Time in milliseconds which are used to create buckets.
   */
  BucketTimedMetric getBucketTimedMetric(MetricName name, int... bucketRanges);
  
  /**
   * Return the CounterMetric using the metric name.
   */
  CounterMetric getCounterMetric(MetricName name);

  /**
   * Return the ValueMetric using the metric name.
   */
  ValueMetric getValueMetric(MetricName name);
  
  /**
   * Return the TimedMetricGroup using the given base metric name.
   */
  TimedMetricGroup getTimedMetricGroup(MetricName baseName);

  /**
   * Return the MetricNameCache using the class as a base name.
   */
  MetricNameCache getMetricNameCache(Class<?> cls);

  /**
   * Return the MetricNameCache using a MetricName as a base name.
   */
  MetricNameCache getMetricNameCache(MetricName baseName);

  /**
   * Return the collection of metrics that are considered non-empty. This means these are metrics
   * that have collected statistics since the last time they were collected.
   */
  Collection<Metric> collectNonEmptyMetrics();

  /**
   * Return a collection of all the metrics.
   */
  Collection<Metric> getMetrics();

  /**
   * Return a collection of the JVM metrics.
   */
  Collection<Metric> getJvmMetrics();

  /**
   * Create and register a GaugeMetric using the gauge supplied (double values).
   */
  GaugeDoubleMetric register(MetricName name, GaugeDouble gauge);

  /**
   * Create and register a GaugeCounterMetric using the gauge supplied (long values).
   */
  GaugeLongMetric register(MetricName name, GaugeLong gauge);

  /**
   * When a request completes it is reported to the manager.
   */
  void reportTiming(RequestTiming requestTiming);

  /**
   * Return the request timings that have been collected since the last collection.
   */
  List<RequestTiming> collectRequestTimings();

}