package org.avaje.metric.spi;

import java.util.Collection;

import org.avaje.metric.CounterMetric;
import org.avaje.metric.Gauge;
import org.avaje.metric.GaugeCounter;
import org.avaje.metric.GaugeCounterMetric;
import org.avaje.metric.GaugeMetric;
import org.avaje.metric.Metric;
import org.avaje.metric.MetricName;
import org.avaje.metric.MetricNameCache;
import org.avaje.metric.TimedMetric;
import org.avaje.metric.TimedMetricGroup;
import org.avaje.metric.ValueMetric;

public interface PluginMetricManager {

  public TimedMetric getTimedMetric(String name);

  public TimedMetric getTimedMetric(MetricName name);

  public CounterMetric getCounterMetric(MetricName name);

  public ValueMetric getValueMetric(MetricName name);

  public TimedMetricGroup getTimedMetricGroup(MetricName baseName);

  public MetricNameCache getMetricNameCache(Class<?> klass);

  public MetricNameCache getMetricNameCache(MetricName baseName);

  public void clear();

  public Collection<Metric> collectNonEmptyMetrics();

  public Collection<Metric> getMetrics();

  public Collection<Metric> getJvmMetrics();

  public MetricName name(Class<?> cls, String eventName);

  public MetricName name(String group, String type, String name);

  public MetricName nameParse(String name);

  public GaugeMetric registerGauge(MetricName name, Gauge gauge);

  public GaugeCounterMetric registerGauge(MetricName name, GaugeCounter gauge);


}