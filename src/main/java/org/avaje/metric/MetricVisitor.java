package org.avaje.metric;



/**
 * Visitor for the statistics held by a metric.
 */
public interface MetricVisitor {

  /**
   * Visit a TimedMetric.
   */
  public void visit(TimedMetric metric);

  /**
   * Visit a ValueMetric.
   */
  public void visit(ValueMetric metric);
  
  /**
   * Visit a CounterMetric.
   */
  public void visit(CounterMetric metric);

  /**
   * Visit an individual GaugeMetric.
   */
  public void visit(GaugeMetric gaugeMetric);

  /**
   * Visit a GaugeMetricGroup.
   */
  public void visit(GaugeMetricGroup gaugeMetricGroup);

  /**
   * Visit an individual IntGaugeMetric.
   */
  public void visit(GaugeCounterMetric gaugeMetric);
  
  /**
   * Visit a IntGaugeMetricGroup.
   */
  public void visit(GaugeCounterMetricGroup gaugeMetricGroup);
}
