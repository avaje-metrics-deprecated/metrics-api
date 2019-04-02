package org.avaje.metric;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.avaje.metric.spi.SpiMetricManager;
import org.avaje.metric.statistics.MetricStatistics;

/**
 * Manages the creation and registration of Metrics.
 * <p>
 * Provides methods to allow agents to go through the registered metrics and gather/report the
 * statistics.
 * <p>
 * This uses a service locator to initialise a underlying PluginMetricManager instance. A default
 * implementation of PluginMetricManager is provided by <em>avaje-metric-core</em>.
 */
public class MetricManager {

  /**
   * The implementation that is found via service loader.
   */
  private static final SpiMetricManager mgr = initialiseProvider();

  /**
   * The default implementation which is avaje-metric-core.
   */
  private static final String DEFAULT_PROVIDER = "org.avaje.metric.core.DefaultMetricManager";

  /**
   * Finds and returns the implementation of PluginMetricManager using the ServiceLoader.
   */
  private static SpiMetricManager initialiseProvider() {

    ServiceLoader<SpiMetricManager> loader = ServiceLoader.load(SpiMetricManager.class);
    Iterator<SpiMetricManager> it = loader.iterator();
    if (it.hasNext()) {
      return it.next();
    }
    try {
      Class<?> clazz = Class.forName(DEFAULT_PROVIDER);
      return (SpiMetricManager) clazz.newInstance();

    } catch (ClassNotFoundException e) {
      throw new RuntimeException("Provider " + DEFAULT_PROVIDER + " not found", e);

    } catch (Exception e) {
      throw new RuntimeException("Provider " + DEFAULT_PROVIDER + " could not be instantiated: " + e, e);
    }
  }

  /**
   * When a request completes it is reported to the manager.
   */
  public static void reportTiming(RequestTiming requestTiming) {
    mgr.reportTiming(requestTiming);
  }

  /**
   * Add a metric supplier to the manager. These metrics are then included in the reporting.
   */
  public static void addSupplier(MetricSupplier supplier) {
    mgr.addSupplier(supplier);
  }

  /**
   * Create a MetricName based on a class and name.
   * <p>
   * Often the name maps to a method name.
   */
  public static MetricName name(Class<?> cls, String name) {
    return mgr.name(cls, name);
  }

  /**
   * Create a Metric name by parsing a name that is expected to include periods (dot notation
   * similar to package.Class.method).
   */
  public static MetricName name(String name) {
    return mgr.name(name);
  }

  /**
   * Return a MetricNameCache for the given class.
   * <p>
   * The MetricNameCache can be used to derive MetricName objects dynamically with relatively less
   * overhead.
   * </p>
   */
  public static MetricNameCache getMetricNameCache(Class<?> cls) {
    return mgr.getMetricNameCache(cls);
  }

  /**
   * Return a MetricNameCache for a given base metric name.
   * <p>
   * The MetricNameCache can be used to derive MetricName objects dynamically with relatively less
   * overhead.
   * </p>
   */
  public static MetricNameCache getMetricNameCache(MetricName baseName) {
    return mgr.getMetricNameCache(baseName);
  }

  /**
   * Return a BucketTimedMetric given the name and bucket ranges.
   */
  public static TimedMetric getTimedMetric(MetricName name, int... bucketRanges) {
    return mgr.getTimedMetric(name, bucketRanges);
  }

  /**
   * Return a BucketTimedMetric given the name and bucket ranges.
   */
  public static TimedMetric getTimedMetric(Class<?> cls, String name, int... bucketRanges) {
    return getTimedMetric(name(cls, name), bucketRanges);
  }

  /**
   * Return a BucketTimedMetric given the name and bucket ranges.
   */
  public static TimedMetric getTimedMetric(String name, int... bucketRanges) {
    return getTimedMetric(name(name), bucketRanges);
  }

  /**
   * Return a TimedMetric given the name.
   */
  public static TimedMetric getTimedMetric(MetricName name) {
    return mgr.getTimedMetric(name);
  }

  /**
   * Return a TimedMetric using the Class, name to derive the MetricName.
   */
  public static TimedMetric getTimedMetric(Class<?> cls, String eventName) {
    return getTimedMetric(name(cls, eventName));
  }

  /**
   * Return a TimedMetric given the name.
   */
  public static TimedMetric getTimedMetric(String name) {
    return getTimedMetric(name(name));
  }

  /**
   * Return a CounterMetric given the name.
   */
  public static CounterMetric getCounterMetric(MetricName name) {
    return mgr.getCounterMetric(name);
  }

  /**
   * Return a CounterMetric given the name.
   */
  public static CounterMetric getCounterMetric(String name) {
    return getCounterMetric(name(name));
  }

  /**
   * Return a CounterMetric using the Class and name to derive the MetricName.
   */
  public static CounterMetric getCounterMetric(Class<?> cls, String eventName) {
    return getCounterMetric(name(cls, eventName));
  }

  /**
   * Return a ValueMetric given the name.
   */
  public static ValueMetric getValueMetric(MetricName name) {
    return mgr.getValueMetric(name);
  }

  /**
   * Return a ValueMetric using the Class and name to derive the MetricName.
   */
  public static ValueMetric getValueMetric(Class<?> cls, String eventName) {
    return getValueMetric(name(cls, eventName));
  }

  /**
   * Return a ValueMetric given the name.
   */
  public static ValueMetric getValueMetric(String name) {
    return getValueMetric(name(name));
  }

  /**
   * Return the TimedMetricGroup with a based metric name.
   */
  public static TimedMetricGroup getTimedMetricGroup(MetricName baseName) {
    return mgr.getTimedMetricGroup(baseName);
  }

  /**
   * Return the TimedMetricGroup with a class providing the base metric name.
   * <p>
   * The package name is the 'group' and the simple class name the 'type'.
   */
  public static TimedMetricGroup getTimedMetricGroup(Class<?> cls) {
    return getTimedMetricGroup(name(cls,""));
  }

  /**
   * Return a TimedMetricGroup with a common group and type name.
   *
   * @param name
   *          the metric name
   *
   * @return the TimedMetricGroup used to create TimedMetric's that have a common base name.
   */
  public static TimedMetricGroup getTimedMetricGroup(String name) {
    return getTimedMetricGroup(MetricName.of(name));
  }

  /**
   * Create and register a GaugeMetric using the gauge supplied.
   */
  public static GaugeDoubleMetric register(MetricName name, GaugeDouble gauge) {
    return mgr.register(name, gauge);
  }

  /**
   * Create and register a GaugeMetric using the gauge supplied.
   */
  public static GaugeDoubleMetric register(String name, GaugeDouble gauge) {
    return mgr.register(name(name), gauge);
  }

  /**
   * Create and register a GaugeCounterMetric using the gauge supplied.
   */
  public static GaugeLongMetric register(MetricName name, GaugeLong gauge) {
    return mgr.register(name, gauge);
  }

  /**
   * Create and register a GaugeCounterMetric using the gauge supplied.
   */
  public static GaugeLongMetric register(String name, GaugeLong gauge) {
    return mgr.register(name(name), gauge);
  }

  /**
   * Return all the non-JVM registered metrics.
   */
  public static Collection<Metric> getMetrics() {
    return mgr.getMetrics();
  }

  /**
   * Return the core JVM metrics.
   */
  public static Collection<Metric> getJvmMetrics() {
    return mgr.getJvmMetrics();
  }

  /**
   * Return all the non-JVM registered metrics that are not empty.
   */
  public static List<MetricStatistics> collectNonEmptyMetrics() {
    return mgr.collectNonEmptyMetrics();
  }

  /**
   * Return JVM metrics that are not empty.
   */
  public static List<MetricStatistics> collectNonEmptyJvmMetrics() {
    return mgr.collectNonEmptyJvmMetrics();
  }

  /**
   * Return the built in JVM metrics support to register collection of all or some
   * of the built in JVM metrics.
   */
  public static JvmMetrics jvmMetrics() {
    return mgr;
  }

  /**
   * Return the API for managing request timing.
   */
  public static RequestTimingManager requestTimingManager() {
    return mgr;
  }

}
