package org.avaje.metric;

/**
 * Standard JVM metrics built in that we often register.
 */
public interface JvmMetrics {

  /**
   * Register all the standard JVM metrics - memory, threads, gc, os load and process memory.
   *
   * @param reportChangesOnly When true only report metrics when the value changes.
   */
  void registerStandardJvmMetrics(boolean reportChangesOnly);

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
   *
   * @param reportChangesOnly When true only report metrics when the value changes.
   */
  void registerJvmThreadMetrics(boolean reportChangesOnly);

  /**
   * Register metrics for heap and non-heap memory.
   *
   * @param reportChangesOnly When true only report metrics when the value changes.
   */
  void registerJvmMemoryMetrics(boolean reportChangesOnly);

  /**
   * Register metrics for VMRSS process memory (if supported on the platform).
   *
   * @param reportChangesOnly When true only report metrics when the value changes.
   */
  void registerJvmProcessMemoryMetrics(boolean reportChangesOnly);

}
