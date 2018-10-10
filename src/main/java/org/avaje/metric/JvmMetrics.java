package org.avaje.metric;

/**
 * Standard JVM metrics built in that we often register.
 */
public interface JvmMetrics {

  /**
   * Set to only report when the metrics change. This is the default and means
   * that metrics that don't change are not reported.
   */
  JvmMetrics withReportChangesOnly();

  /**
   * Set to report the metrics irrespective of whether the metric has changed.
   * <p>
   * For metrics that generally don't change like max memory or don't change as
   * frequently these metrics will be reported every time.
   * </p>
   */
  JvmMetrics withReportAlways();

  /**
   * Register all the standard JVM metrics - memory, threads, gc, os load and process memory.
   */
  void registerStandardJvmMetrics();

  /**
   * Register a metric for OS load.
   */
  void registerJvmOsLoadMetric();

  /**
   * Register metrics for GC activity.
   */
  void registerJvmGCMetrics();

  /**
   * Register metrics for the total number of threads allocated.
   */
  void registerJvmThreadMetrics();

  /**
   * Register metrics for heap and non-heap memory.
   */
  void registerJvmMemoryMetrics();

  /**
   * Register metrics for VMRSS process memory (if supported on the platform).
   */
  void registerJvmProcessMemoryMetrics();

}
