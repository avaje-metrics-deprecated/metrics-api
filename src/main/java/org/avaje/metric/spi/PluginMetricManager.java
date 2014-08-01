package org.avaje.metric.spi;

import java.util.Collection;

import org.avaje.metric.CounterMetric;
import org.avaje.metric.GaugeDouble;
import org.avaje.metric.GaugeLong;
import org.avaje.metric.GaugeLongMetric;
import org.avaje.metric.GaugeDoubleMetric;
import org.avaje.metric.Metric;
import org.avaje.metric.MetricName;
import org.avaje.metric.MetricNameCache;
import org.avaje.metric.TimedMetric;
import org.avaje.metric.TimedMetricGroup;
import org.avaje.metric.ValueMetric;

/**
 * The SPI for the underlying implementation that is plugged in via service locator.
 */
public interface PluginMetricManager {

  /**
   * Return the TimedMetric using the metric name given in string dot notation.
   */
  public TimedMetric getTimedMetric(String name);

  /**
   * Return the TimedMetric using the metric name.
   */
  public TimedMetric getTimedMetric(MetricName name);

  /**
   * Return the CounterMetric using the metric name.
   */
  public CounterMetric getCounterMetric(MetricName name);

  /**
   * Return the CounterMetric using the metric name.
   */
  public CounterMetric getCounterMetric(String name);

  /**
   * Return the CounterMetric using the Class and eventName to define the metric name.
   */
  public CounterMetric getCounterMetric(Class<?> cls, String eventName);

  /**
   * Return a ValueMetric using given the full metric name.
   */
  public ValueMetric getValueMetric(String name);

  /**
   * Return the ValueMetric using the metric name.
   */
  public ValueMetric getValueMetric(MetricName name);

  /**
   * Return a ValueMetric using the Class and name to derive the MetricName.
   */
  public ValueMetric getValueMetric(Class<?> cls, String eventName);

  /**
   * Return the TimedMetricGroup using the given base metric name.
   */
  public TimedMetricGroup getTimedMetricGroup(MetricName baseName);

  /**
   * Return the MetricNameCache using the class as a base name.
   */
  public MetricNameCache getMetricNameCache(Class<?> cls);

  /**
   * Return the MetricNameCache using a MetricName as a base name.
   */
  public MetricNameCache getMetricNameCache(MetricName baseName);

  /**
   * Return the collection of metrics that are considered non-empty. This means these are metrics
   * that have collected statistics since the last time they were collected.
   */
  public Collection<Metric> collectNonEmptyMetrics();

  /**
   * Return a collection of all the metrics.
   */
  public Collection<Metric> getMetrics();

  /**
   * Return a collection of the JVM metrics.
   */
  public Collection<Metric> getJvmMetrics();

  /**
   * Create a MetricName based on the class and name.
   * Typically name is a method name.
   */
  public MetricName name(Class<?> cls, String name);

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
  public MetricName name(String group, String type, String name);

  /**
   * Create a Metric name by parsing a name that is expected to include periods.
   * <p>
   * The name is expected to be in dot notation similar to <code>package.class.method</code>.
   */
  public MetricName name(String name);

  /**
   * Create and register a GaugeMetric using the gauge supplied (double values).
   */
  public GaugeDoubleMetric registerGauge(MetricName name, GaugeDouble gauge);

  /**
   * Create and register a GaugeMetric using the gauge supplied (double values).
   */
  public GaugeDoubleMetric registerGauge(String name, GaugeDouble gauge);

  /**
   * Create and register a GaugeCounterMetric using the gauge supplied (long values).
   */
  public GaugeLongMetric registerGauge(MetricName name, GaugeLong gauge);

  /**
   * Create and register a GaugeCounterMetric using the gauge supplied (long values).
   */
  public GaugeLongMetric registerGauge(String name, GaugeLong gauge);


}