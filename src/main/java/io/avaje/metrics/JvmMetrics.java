package io.avaje.metrics;

/**
 * Standard JVM metrics built in that we often register.
 * <p>
 * Typically we want the standard JVM metrics and either Logback or Log4J metrics
 * and this provides a relatively easy way to register those.
 * </p>
 * <pre>{@code
 *
 *   MetricManager.jvmMetrics()
 *     .withReportAlways()
 *     .registerStandardJvmMetrics()
 *     .registerLogbackMetrics();
 *
 * }</pre>
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
  JvmMetrics registerStandardJvmMetrics();

  /**
   * Register a metric for OS load.
   */
  JvmMetrics registerJvmOsLoadMetric();

  /**
   * Register metrics for GC activity.
   */
  JvmMetrics registerJvmGCMetrics();

  /**
   * Register metrics for the total number of threads allocated.
   */
  JvmMetrics registerJvmThreadMetrics();

  /**
   * Register metrics for heap and non-heap memory.
   */
  JvmMetrics registerJvmMemoryMetrics();

  /**
   * Register metrics for VMRSS process memory (if supported on the platform).
   */
  JvmMetrics registerJvmProcessMemoryMetrics();

  /**
   * Set the names of the metrics for logging errors and warnings.
   * <p>
   * When not set these default to app.log.error and app.log.warn respectively.
   * </p>
   */
  JvmMetrics withLogMetricName(String errorMetricName, String warnMetricName);

  /**
   * Register metrics for Logback error and warning messages.
   */
  JvmMetrics registerLogbackMetrics();

  /**
   * Register metrics for Log4J error and warning messages.
   */
  JvmMetrics registerLog4JMetrics();

}